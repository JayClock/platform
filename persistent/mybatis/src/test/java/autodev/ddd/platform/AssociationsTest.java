package autodev.ddd.platform;

import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.context.annotation.Import;
import autodev.ddd.platform.model.User;
import autodev.ddd.platform.model.Users;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MybatisTest
@Import(FlywayConfig.class)
@ExtendWith(TestDataSetup.class)
public class AssociationsTest {

    @Inject
    private Users users;

    private User user;

    @BeforeEach
    public void setup() {
        String customerId = "1";
        user = users.findById(customerId).get();
    }


    @Test
    public void should_find_customer_by_id() {
        assertEquals("1", user.getIdentity());
        assertEquals("John Smith", user.getDescription().name());
        assertEquals("john.smith@email.com", user.getDescription().email());
    }

    @Test
    public void should_not_find_customer_if_not_exist() {
        assertTrue(users.findById("-1").isEmpty());
    }
}
