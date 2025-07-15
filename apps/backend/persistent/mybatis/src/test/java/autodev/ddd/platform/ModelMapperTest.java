package autodev.ddd.platform;

import autodev.ddd.platform.model.Purchaser;
import autodev.ddd.platform.mybatis.PurchasersMapper;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import autodev.ddd.platform.mybatis.UsersMapper;
import autodev.ddd.platform.model.User;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MybatisTest
public class ModelMapperTest {
    @Inject
    private UsersMapper usersMapper;
    @Inject
    private PurchasersMapper purchasersMapper;
    @Inject
    private TestDataMapper testData;

    private final String userId = id();

    private static String id() {
        return String.valueOf(new Random().nextInt(100000));
    }

    @BeforeEach
    public void before() {
        testData.insertUser(userId, "John Smith", "john.smith@email.com");
        testData.insertPurchaser(userId, BigDecimal.valueOf(1000));
        testData.insertPurchase("123", userId, new Date());
    }

    @Test
    public void should_find_user_by_id() {
        User user = usersMapper.findUserById(userId);
        assertEquals(user.getIdentity(), userId);
        assertEquals("john.smith@email.com", user.getDescription().email());
        assertEquals("John Smith", user.getDescription().name());
    }

    @Test
    public void should_assign_purchase_association() {
        Purchaser purchaser = purchasersMapper.findPurchaserByUserId(userId);
        assertEquals(1, purchaser.purchases().findAll().size());
    }
}
