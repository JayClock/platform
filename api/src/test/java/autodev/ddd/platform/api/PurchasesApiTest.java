package autodev.ddd.platform.api;

import autodev.ddd.platform.description.PurchaseDescription;
import autodev.ddd.platform.description.PurchaserDescription;
import autodev.ddd.platform.description.UserDescription;
import autodev.ddd.platform.model.Purchase;
import autodev.ddd.platform.model.Purchaser;
import autodev.ddd.platform.model.User;
import autodev.ddd.platform.model.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

public class PurchasesApiTest extends ApiTest {
    private static final Logger log = LoggerFactory.getLogger(PurchasesApiTest.class);
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
    public void should_find_all_purchases() {
        when(users.findById(user.getIdentity())).thenReturn(Optional.of(user));
        when(users.inPurchaseContext()).thenReturn(purchaseContext);
        when(purchaseContext.asPurchaser(user)).thenReturn(purchaser);
        Purchase purchase = new Purchase("123", new PurchaseDescription(new Date()));
        when(purchaser.purchases().findAll()).thenReturn(new EntityList<>(purchase));

        given().accept(MediaTypes.HAL_JSON.toString())
                .when().get("/users/" + user.getIdentity() + "/purchaser/purchases")
                .then().statusCode(200)
                .body("_links.self.href", is("/api/users/" + user.getIdentity() + "/purchaser/purchases?page=0"))
                .body("_embedded.purchases.size()",is(1))
                .body("_embedded.purchases[0].id",is(purchase.getIdentity()));
    }
}
