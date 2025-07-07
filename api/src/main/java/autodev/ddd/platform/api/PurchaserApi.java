package autodev.ddd.platform.api;

import autodev.ddd.platform.api.representation.PurchaserModel;
import autodev.ddd.platform.model.Purchaser;
import autodev.ddd.platform.model.User;
import autodev.ddd.platform.model.Users;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;

public class PurchaserApi {
    private final Purchaser purchaser;

    public PurchaserApi(Users users, User user) {
        this.purchaser = users.inPurchaseContext().asPurchaser(user);
    }

    @GET
    public PurchaserModel get(@Context UriInfo uriInfo) {
        return new PurchaserModel(purchaser, uriInfo);
    }

    @Path("/purchases")
    public PurchasesApi purchases() {
        return new PurchasesApi(purchaser);
    }
}
