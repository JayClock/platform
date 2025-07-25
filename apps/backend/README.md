众所周知，企业在从事商业活动时，以合同作为支撑。通过合同界定参与方的义务及权利，保障及规范参与方的履约行为。同时，向相关监管、审计方提供商业活动中的履约凭证，以此证明商业活动的合法、合规、合理性。

在 B 端中，我们的合同上下文，是销售与客户之间的沟通桥梁。只有客户用上合同中的功能，才能正式的签单与付费。

在 C 端中，我们的合同上下文，则是我们一直忽视的“用户协议”（就是你登陆和付款时，看都不看的那部分），如果我们购买了视频网站的会员，但会员专属视频不能查看，那么我们就可以向 12315 举报。

上面的例子都是涉及到直接金钱交易的，但我们还有很多系统，不会涉及到金钱相关的内容。对于 CRM 系统，则是《劳动法》约束下的 kpi 协议，kpi 多次不达标则可以进行裁员。对于学校的选课系统，则是《中华人民共和国高等教育法》约束下的学生手册部分，只有拿到对应的学分，你才能拿到毕业证书。

既然业务方在基于**履约上下文**，来和客户进行交易，并养活整个公司，那么自然的，研发侧为了**让履约成立**，最合理有效的方式，就是完全拥抱业务视角，和业务一起，把履约上下文中的业务信息提取出来。并转化为**模型和通用语言**，以此建立双方的沟通桥梁。
## 合同的生命周期

对于大多数的合同的生命周期，主要可以分为：询价、报价、合同、履约四个阶段
1. 邀请投标：你想做一个东西，谁可以做，怎么做，需要多少钱等都属于询价的过程。
2. 投标：响应询价的过程。
3. 合同：指合同的签订，是合同生命周期中最重要的节点，因为合同签订前的活动不具备法律效力，签订合同的节点才意味着真正产生法律效力。
4. 履约请求：合同生效后，参与方对合同规定的义务及权利进行履约的过程。过程中会产生相应履约凭证，以此证明参与方履行了相关义务与权利。
5. 履约确认：

举一个简单的例子：

在电子商务出现以前，家里也没网络和电脑的时候，我为了能够追上当时还在热播的“火影忍者”的进度，我一般会去寻找路边那些一身长衣下，埋藏了大量盗版影碟的摊主。
- 当我凑到摊主旁边问摊主火影怎么买，这时我（甲方）向摊主（已方）作出**投标邀请**
- 摊主把我拉到角落里回答说，两张碟 10 块钱，这就是**投标**。
- 作为从小到大的 i 人，我向来都是默认接受这个投标。
- 于是**口头合同**成立。我掏钱付款，完成支付履约；摊主提供货物，完成交付履约。至此业务执行完毕。

我们如果用 plantuml 进行建模，可以简单建模为如下：
- 从购买方角度来说，购买方有权要求售出方提供合理的商品，这个也是售出方必须承担的义务。
- 从售出方角度来说，售出方有权要求购买方交付合理的金钱，这个业务购买方必须承担的义务。

```plantuml
class 售出方 #yellow {}
class 购买方 #yellow {}
class 商品交易合同 #pink {
	signed_at
	price
}
class 商品 #green {}

'合同参与者
售出方 -- 商品交易合同
购买方 -- 商品交易合同

'要求给出对应的商品，权利方是“购买方”，义务方是“售出方”
class 商品提供请求 #pink {
	start_at
	expire_at
}
class 商品提供确认 #pink {
	confirm_at
}
购买方 -- 商品提供请求
商品提供请求 -- 商品提供确认
商品提供确认 -- 售出方

商品提供请求 -- 商品
商品 -- 商品交易合同


'收到货物后进行打款，权利方是“售出方”，义务方是“购买方”
class 商品付款请求 #pink {
	start_at
	expire_at
}
class 商品付款确认 #pink {
	confirm_at
}
售出方 -- 商品付款请求
商品付款请求 -- 商品付款确认
商品付款确认 -- 购买方
```
如果引入上下文的概念，可以抽象成以下内容

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

- #green 可以是扮演角色的参与方，也可以是具体的人，某件东西，或者某个场所；还可以是凭证有关的标的物，建表时有自己的 id
- #yellow 事件参与方在事件中扮演的角色，建表时没有自己的 id，只会关联 #green 参与方的 id，比如 Purchaser 的 user_id
- #pink 凭证，事件参与方所需的追溯物，建表时有自己的 id