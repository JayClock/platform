
- [ ] 面向对象 CRC 法的应用
- [ ] CRC 和 hateaos 的映射关系
- [ ] N + 1 问题的成因和解决

```plantuml
class User {}

class Purchase {}

User "1" *-- "*" Purchase
```

在应用 ddd 时，我们希望通过充血模型，来做到和业务模型相关联的代码，其中聚合与聚合根（Aggregation Root）是构成“富含知识的模型（Knowledge Rich Model）”的关键,
同过聚合关系，我们可以将被聚合对象的集合逻辑放置于聚合 / 聚合根里，而不是散落在外，或是放在其他无关的服务中。这么做可以使得逻辑富集于模型中，避免“逻辑泄露”。

但是被聚合的对象，通常来自具体的数据库。也就很容易将具体技术引入领域模型，这也有悖于领域驱动设计的理念。比如下面代码中，`getPurchases`方法就不得不在领域层中，引入具体的数据库请求。

```java
public class User {
    public String name;
    
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