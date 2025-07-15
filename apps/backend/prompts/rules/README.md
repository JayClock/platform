
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