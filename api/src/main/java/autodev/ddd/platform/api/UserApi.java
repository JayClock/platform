package autodev.ddd.platform.api;

import autodev.ddd.platform.api.representation.PurchaserModel;
import autodev.ddd.platform.api.representation.UserModel;
import autodev.ddd.platform.model.Purchaser;
import autodev.ddd.platform.model.User;
import autodev.ddd.platform.model.Users;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;


public class UserApi {
    private final Users users;
    private final User user;

    public UserApi(Users users,User user) {
        this.users = users;
        this.user = user;
    }

    @GET
    public UserModel get(@Context UriInfo uriInfo) {
        return new UserModel(user, uriInfo);
    }

    @GET
    @Path("/purchaser")
    public PurchaserModel getPurchaser(@Context UriInfo uriInfo) {
        Purchaser purchaser = users.inPurchaseContext().asPurchaser(user);
        return new PurchaserModel(purchaser, uriInfo);
    }
}
