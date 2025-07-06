package autodev.ddd.platform.model;

import autodev.ddd.archtype.Entity;
import autodev.ddd.archtype.HasMany;
import autodev.ddd.platform.description.PurchaserDescription;

public class Purchaser implements Entity<String, PurchaserDescription> {
    private User user;
    private PurchaserDescription description;
    private Purchases purchases;

    public Purchaser(User user, PurchaserDescription description, Purchases purchases) {
        this.user = user;
        this.description = description;
        this.purchases = purchases;
    }

    public Purchaser(User user, PurchaserDescription description) {
        this.user = user;
        this.description = description;
    }

    private Purchaser() {
    }

    @Override
    public String getIdentity() {
        return user.getIdentity();
    }

    @Override
    public PurchaserDescription getDescription() {
        return description;
    }

    public HasMany<String, Purchase> purchases() {
        return purchases;
    }

    public interface Purchases extends HasMany<String, Purchase> {
    }
}
