
```plantuml
class User #yellow {
}

namespace PurchaseContext{
    class Purchaser #green {
    }
    
    class Purchase #green {
    }
    
    Purchaser <-- Purchase
}

namespace SalesContext{
    class Seller #green {
    }
    
    class Sale #green {
    }
    
    Seller <-- Sale
}

User <|.. Purchaser
User <|.. Seller
```