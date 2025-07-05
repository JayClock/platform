package autodev.ddd.platform.mybatis.contexts;

import autodev.ddd.platform.model.Purchaser;
import autodev.ddd.platform.model.User;
import autodev.ddd.platform.model.Users;
import autodev.ddd.platform.mybatis.PurchasersMapper;
import jakarta.inject.Inject;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PurchaseContext implements Users.PurchaseContext {
    private final PurchasersMapper purchasersMapper;

    @Inject
    public PurchaseContext(PurchasersMapper purchasersMapper) {
        this.purchasersMapper = purchasersMapper;
    }

    @Override
    public Purchaser asPurchaser(User user) {
        this.purchasersMapper.createPurchaser(user.getIdentity(), BigDecimal.valueOf(0));
        return this.purchasersMapper.findPurchaserByUserId(user.getIdentity());
    }
}
