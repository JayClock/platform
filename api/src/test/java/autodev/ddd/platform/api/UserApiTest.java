package autodev.ddd.platform.api;

import autodev.ddd.platform.description.PurchaserDescription;
import autodev.ddd.platform.description.UserDescription;
import autodev.ddd.platform.model.Purchaser;
import autodev.ddd.platform.model.User;
import autodev.ddd.platform.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;

import java.math.BigDecimal;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class UserApiTest extends ApiTest {
    @MockBean
    private Users users;

    private User user;
    private Purchaser purchaser;

    @Mock
    private Users.PurchaseContext purchaseContext;

    @Mock
    private Purchaser.Purchases purchases;

    @BeforeEach()
    public void beforeEach() {
        user = new User("john.smith",
                new UserDescription("John Smith", "john.smith@email.com"));
        PurchaserDescription purchaserDescription = new PurchaserDescription(BigDecimal.valueOf(100));
        purchaser = new Purchaser(user, purchaserDescription, purchases);
    }

    @Test
    public void should_convert_purchaser_with_user() {
        when(users.findById(user.getIdentity())).thenReturn(Optional.of(user));
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
