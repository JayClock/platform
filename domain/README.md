
- [ ] 面向对象 CRC 法的应用
- [ ] CRC 和 hateaos 的映射关系
- [ ] N + 1 问题的成因和解决

生成式 AI 能够“很好”地解决创建新项目的问题，但是生成的结果是由大量“最多实践”拼凑而成的 “it just works”。一旦生成后，就变成了人类难以理解和修改的“存量代码”，工程师讲究的是经典的工程化思维
- 理解真实的业务问题 
- 找出合适的解决方案
- 分解更小的任务列表
- 依次解决任务清单

如果没有人类的干预，AI 会直接从编码层面的力大砖飞。虽然现在有大量工具能对整个项目（或指定的模块范围）进行深度解析。包括构建完整的 AST，识别所有的函数、类、接口及其签名、注释（docstrings）。同时，分析项目依赖（内部模块间和外部库依赖），构建初步的依赖图。

但是，由于代码本身已经丢失了过多的上下文信息，即使是使用 augment code 这类以 context 识别为长处的编码工具，由于识别出来的信息无法被人所理解，我们依旧会陷入“复制-提问-教育 ai”的循环，以“能运行”为衡量指标，通过暴力算法"解决问题"。

但实际上，这也仅仅只是将问题延后，后面的开发者必须在更加混乱的上下文进行分析。于是我们就陷入了“越是希望借助 ai 提效，越是陷入无法掌控代码的境地”，AI 会 “不加选择地放大”：如果我们在犯错，它会帮助我们更快、更大规模地犯错。也因此极大地拉大了高质量代码库与低质量代码库之间的开发速度差距。 因此，如何设计高质量代码模板，可以说是第一优先级事项。

由于平时软件开发，90% 的场景都是如何通过代码描述业务，以此来快速实现 crud。如何更好地关联模型与实现，自然是我们第一优先要考虑的事项。

### 面向对象 CRC 卡

CRC 卡是 kent back 在 1989 年，提出的面向对象建模方法，包括三个部分：**类名、类的职责、类的协作关系**，每一张卡片表示一个类。

- **类**：代表一系列对象的集合，这些对象是对系统设计的抽象建模，可以是一个人、一件物品等等，类名写在整个CRC卡的最上方。
- **职责**：包括这个类对自身信息的了解，以及这些信息将如何运用。诸如，一个人，他知道他的电话号码、地址、性别等属性，并且他知道他可以说话、行走的行为能力。这个部分在CRC卡的左边。
- **协作**：指代另一个类，我们通过这个类获取我们想要的信息或者相关操作。这个部分在CRC卡的右边。

```plantuml
class User {
    String id
    String name
    String email
}

class Purchase {
    String id
}

User "1" *-- "*" Purchase
```

在平时软件开发中，我们会通过 UML 模型来精简但准确地传递业务信息。 为了实现和业务模型相关联的代码，其中聚合与聚合根（Aggregation Root）是构成“富含知识的模型（Knowledge Rich Model）”的关键, 同过聚合关系，我们可以将被聚合对象的集合逻辑放置于聚合 / 聚合根里，而不是散落在外，或是放在其他无关的服务中。这么做可以使得逻辑富集于模型中，避免“逻辑泄露”。

但是被聚合的对象，通常来自具体的数据库。也就很容易将具体技术引入领域模型，这也有悖于领域驱动设计的理念。比如下面代码中，`getPurchases`方法就不得不在领域层中，引入具体的数据库请求。

```java
public class User {
    public String id;
    public String name;
    public String email;
    
    public List<Subscription> getPurchases() {
      db.executeQuery();
    }
}
```

为了避免模型层引入具体技术，按照 Spring 推荐的方法，为聚合构造一个独立的 Repository 对象，将逻辑放在里面，比如
```java
interface PurchasesRepository {
   Page<Purchase> findPaginated(int from, int size);
}
```
但是这样又会引起逻辑泄漏，User 也退化为纯粹的无逻辑数据对象（贫血对象）

为了解决这个问题，我们可以**将对象间的关联关系直接建模出来，然后再通过接口与抽象的隔离，把具体技术实现细节封装到接口的实现中**。这样既可以保证概念上的统一，又能够避免技术实现上的限制。

比如现在 User 和 Purchase 是一对多的关系，最直接的关联对象名称是 `UserPurchases`

```java
import java.util.List;

public interface UserPurchases extends Iterable<Purchase> {
    List<Purchase> subList(int from,int to);
}
```

那么原来用户购买的内容就变成了
```java
public class User {
    private UserPurchases userPurchases;

    public User(UserPurchases userPurchases) {
        this.userPurchases = userPurchases;
    }

    UserPurchases getUserPurchases() {
        return userPurchases;
    }
}
```

相应的分页调用也变为了

```java
    user.getUserPurchases().subList(0,10);
```

在这里，User 是 Purchase 的聚合根，那么与之相关的逻辑，也通过关联对象，封装在了 User 的上下文中。同时，对于持久化的技术逻辑，也通过**接口与实现代码分离**，完美地从领域对象中，移除掉了对技术实现的依赖。

```java
public class UserPurchasesDB implements UserPurchases {
    List<Purchase> subList(int from,int to){
        mapper.subList(from,to);
    }
}
```

当然还有一个问题：**如何将 UserPurchasesDB 传递给 User？** 这里我们可以实现一个根关联对象 **Users**，并借助框架的依赖注入工具，来完成关联对象的设置。
```java
public class UserPurchasesDB implements UserPurchases {
}

public class UsersDB implements Users {
    private UserPurchases userPurchases;
    
    @Inject
    public class UsersDB(UserPurchases userPurchases) {
    }
    
    findBy(long id) {
        db.executeQuery(...)
        return new User(userPurchases);
    }
}
```

现在我们构造一个更加复杂的场景，用户既可以购买商品，也可以售出商品
```plantuml
class User {}

class Sale {}

class Purchase {}
User "1" *-- "*" Purchase
User "1" *-- "*" Sale
```
于是我们的 User 类就变成了如下样式
```plantuml
class User {
    // 售出相关的
    totalEarned
    saleSomething(){}
    getSales(){}

    // 购买相关的
    purchaseSomething(){}
    getPurchases(){}
}
```
我们的确按照**富含知识的充血模型**的要求，构造出了一个符合要求的对象，但是也很容易带来一个问题：**一个对象中包含了不同的上下文**，也就是所谓的过大类（****）。而过大类又会带来以下问题

首先是**模型僵硬**。想要理解这个类的行为，就必须理解所有的上下文。而只有理解了所有的上下文，才能判断其中的代码和行为是否合理。

于是，**上下文的过载就变成了认知的过载（Cognitive Overloading），而认知过载就会造成维护的困难**。通俗地讲，就是“**看不懂、改不动**”的“祖传代码”（想着用 ai 继续在上面添功能的，建议别干开发了）。

领域驱动设计的默认风格中，逻辑都汇聚于实体，按照构建“富含知识的模型”的做法，聚合对象的集合逻辑自然放置于聚合 / 聚合根里，而不是散落在外，或是放在其他无关的服务中，借此做到逻辑富集于模型。

但是，这种形式也缺少有效分离不同上下文的方式，每当 User 在不同的上下文中处于聚合 / 聚合根位置的时候，与之相关的逻辑都会进入 User 中，User 会不可避免地膨胀。与之相比，DCI 范型要求显式地建模上下文中的角色对象，逻辑汇聚于角色上。如果按照 DCI 的思路，我们可以如下图这样建立模型

```plantuml
class User #green {
}

namespace PurchaseContext{
    class Purchaser #yellow {
    }
    
    class Purchase #pink {
    }
    
    Purchaser *-- Purchase
}

namespace SalesContext{
    class Seller #yellow {
    }
    
    class Sale #pink {
    }
    
    Seller *-- Sale
}

User .. Purchaser
User .. Seller
```

**在不同的上下文中，用户是以不同的角色与其他对象发生交互的，而一旦离开了对应的上下文，相关的交互也就不会发生了。**

这与我们生活中的体验是一致的。上班时，以员工的身份发生的工作活动与交互，下班之后其实可以不用发生了。因为已经脱离了工作这个上下文，不再扮演这个角色了。同样的，在生活中的以其他身份发生的活动与交互，在工作中也不该发生。

从 DCI 的角度看待聚合与聚合根的关系，并不是 User 聚合了 Purchase 与 Sale，而是 PurchaseContext 中，Purchaser 聚合了 Purchase，以及 SaleContext 中，Seller 聚合了 Sale。**User 只是恰好在不同的上下文中扮演了这些角色而已**

于是我们可以吧上下文和角色对象都建模出来

```plantuml
public class Purchaser {
    
}

public interface Users {
    Optional<User> findById(String id);

    PurchaseContext inPurchaseContext();

    interface PurchaseContext {
        Purchaser asPurchaser(User user);
    }
}
```

于是，当我们需要进入购买上下文时，只需要如下形式

```plantuml
User user = users.findById("123")
Purchaser purchaser = users.inPurchaseContext.asPurchaser()
```

这样，我们就完美的，将软件实现和模型关联起来，并且，并从语义层面上，揭示了领域层面的上下文切换意图