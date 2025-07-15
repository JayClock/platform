package autodev.ddd.platform.mybatis.associations;

import autodev.ddd.platform.model.User;
import autodev.ddd.platform.mybatis.UsersMapper;
import jakarta.inject.Inject;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class Users implements autodev.ddd.platform.model.Users {
    private final UsersMapper mapper;
    private final PurchaseContext purchaseContext;

    @Inject
    public Users(UsersMapper mapper, PurchaseContext purchaseContext) {
        this.mapper = mapper;
        this.purchaseContext = purchaseContext;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(mapper.findUserById(id));
    }

    @Override
    public PurchaseContext inPurchaseContext() {
        return purchaseContext;
    }
}
