package autodev.ddd.platform;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import autodev.ddd.platform.model.Users;

public class TestDataSetup implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        ApplicationContext springContext = SpringExtension.getApplicationContext(context);
        TestDataMapper testData = springContext.getBean(TestDataMapper.class);
        Users users = springContext.getBean(Users.class);

        String userId = "1";
        testData.insertUser(userId, "John Smith", "john.smith@email.com");
    }
}
