package autodev.ddd.platform.model;

import autodev.ddd.archtype.Entity;
import autodev.ddd.platform.description.PurchaserDescription;

public class Purchaser implements Entity<String, PurchaserDescription> {
    private User user;
    private PurchaserDescription description;

    public Purchaser(User user, PurchaserDescription description) {
        this.user = user;
        this.description = description;
    }

    private Purchaser(){}

    @Override
    public String getIdentity() {
        return user.getIdentity();
    }

    @Override
    public PurchaserDescription getDescription() {
        return description;
    }
}
