package autodev.ddd.platform.api;

import autodev.ddd.platform.description.PurchaserDescription;
import autodev.ddd.platform.description.UserDescription;
import autodev.ddd.platform.model.Purchaser;
import autodev.ddd.platform.model.User;
import autodev.ddd.platform.model.Users;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;

import java.math.BigDecimal;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserApiTest extends ApiTest {
    @MockBean
    private Users users;

    @Test
    public void should_return_purchaser_if_user_exists() {
        User user = new User("john.smith",
                new UserDescription("John Smith", "john.smith@email.com"));
        PurchaserDescription purchaserDescription = new PurchaserDescription(BigDecimal.valueOf(100));
        Purchaser purchaser = new Purchaser(user, purchaserDescription);

        when(users.findById(user.getIdentity())).thenReturn(Optional.of(user));
        Users.PurchaseContext purchaseContext = mock(Users.PurchaseContext.class);
        when(users.inPurchaseContext()).thenReturn(purchaseContext);
        when(purchaseContext.asPurchaser(user)).thenReturn(purchaser);

        given().accept(MediaTypes.HAL_JSON.toString())
                .when().get("/users/" + user.getIdentity() + "/purchaser")
                .then().statusCode(200)
                .body("id", is(user.getIdentity()))
                .body("amount", is(purchaser.getDescription().amount().intValue()))
                .body("_links.self.href", is("/api/users/" + user.getIdentity() + "/purchaser"));
    }
}
