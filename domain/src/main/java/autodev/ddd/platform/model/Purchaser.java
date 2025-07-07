package autodev.ddd.platform.model;

import autodev.ddd.archtype.Entity;
import autodev.ddd.archtype.HasMany;
import autodev.ddd.platform.description.PurchaserDescription;

public class Purchaser implements Entity<String, PurchaserDescription> {
    private String identity;
    private PurchaserDescription description;
    private Purchases purchases;

    public Purchaser(String identity, PurchaserDescription description, Purchases purchases) {
        this.identity = identity;
        this.description = description;
        this.purchases = purchases;
    }

    private Purchaser() {
    }

    @Override
    public String getIdentity() {
        return this.identity;
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
