package autodev.ddd.platform.api;

import autodev.ddd.platform.model.Users;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/users")
public class UsersApi {
    private final Users users;

    @Inject
    public UsersApi(Users users) {
        this.users = users;
    }

    @Path("{id}")
    public UserApi findById(@PathParam("id") String id) {
        return users.findById(id).map(user -> new UserApi(users, user)).orElse(null);
    }
}