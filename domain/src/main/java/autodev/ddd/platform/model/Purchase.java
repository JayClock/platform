package autodev.ddd.platform.model;

import autodev.ddd.archtype.Entity;
import autodev.ddd.platform.description.PurchaseDescription;

public class Purchase implements Entity<String, PurchaseDescription> {
    private String identity;
    private PurchaseDescription description;

    public Purchase(String identity, PurchaseDescription description) {
        this.identity = identity;
        this.description = description;
    }

    private Purchase() {
    }

    @Override
    public String getIdentity() {
        return identity;
    }

    @Override
    public PurchaseDescription getDescription() {
        return description;
    }
}
