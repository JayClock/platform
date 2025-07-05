package autodev.ddd.platform.model;

import java.util.Optional;

public interface Users {
    Optional<User> findById(String id);

    PurchaseContext inPurchaseContext();

    interface PurchaseContext {
        Purchaser asPurchaser(User user);
    }
}

