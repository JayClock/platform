package autodev.ddd.platform.api.representation;

import autodev.ddd.platform.api.ApiTemplates;
import autodev.ddd.platform.description.UserDescription;
import autodev.ddd.platform.model.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.ws.rs.core.UriInfo;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;


public class UserModel extends RepresentationModel<UserModel> {
    @JsonProperty
    private String id;
    @JsonUnwrapped
    private UserDescription description;

    public UserModel(User user, UriInfo uriInfo) {
        this.id = user.getIdentity();
        this.description = user.getDescription();
        add(Link.of(ApiTemplates.user(uriInfo).build(user.getIdentity()).getPath(), "self"));
        add(Link.of(ApiTemplates.purchaser(uriInfo).build(user.getIdentity()).getPath(), "purchaser"));
    }
}

