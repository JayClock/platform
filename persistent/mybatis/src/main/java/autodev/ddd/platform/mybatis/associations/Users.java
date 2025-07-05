package autodev.ddd.platform.mybatis.associations;

import jakarta.inject.Inject;
import org.springframework.stereotype.Component;
import autodev.ddd.platform.mybatis.UsersMapper;
import autodev.ddd.platform.model.User;

import java.util.Optional;

@Component
public class Users implements autodev.ddd.platform.model.Users {
    private final UsersMapper mapper;

    @Inject
    public Users(UsersMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(mapper.findUserById(id));
    }
}
