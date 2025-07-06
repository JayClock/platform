package autodev.ddd.platform.api;

import autodev.ddd.platform.api.representation.PurchaserModel;
import autodev.ddd.platform.model.Purchaser;
import autodev.ddd.platform.model.User;
import autodev.ddd.platform.model.Users;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;

public class PurchaserApi {
    private final User user;
    private final Users users;

    public PurchaserApi(Users users, User user) {
        this.user = user;
        this.users = users;
    }

    @GET
    public PurchaserModel get(@Context UriInfo uriInfo) {
        Purchaser purchaser = users.inPurchaseContext().asPurchaser(user);
        return new PurchaserModel(purchaser, uriInfo);
    }
}
