package autodev.ddd.platform.api;

import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;

public class ApiTemplates {
    public static UriBuilder user(UriInfo uriInfo) {
        return uriInfo.getBaseUriBuilder().path(UsersApi.class).path(UsersApi.class, "findById");
    }

    public static UriBuilder purchaser(UriInfo uriInfo) {
        return user(uriInfo).path(UserApi.class, "getPurchaser");
    }
}
