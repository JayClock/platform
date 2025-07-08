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
