package autodev.ddd.platform.api.representation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.ws.rs.core.UriInfo;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import autodev.ddd.platform.api.UsersApi;
import autodev.ddd.platform.description.UserDescription;
import autodev.ddd.platform.model.User;


public class UserModel extends RepresentationModel<UserModel> {
    @JsonProperty
    private String id;
    @JsonUnwrapped
    private UserDescription description;

    public UserModel(User user, UriInfo uriInfo) {
        this.id = user.getIdentity();
        this.description = user.getDescription();
        add(Link.of(uriInfo.getBaseUriBuilder().path(UsersApi.class).path(UsersApi.class, "findById").build(user.getIdentity()).getPath(), "self"));
    }
}

