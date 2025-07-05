package autodev.ddd.platform.mybatis;

import org.apache.ibatis.annotations.Mapper;
import autodev.ddd.platform.model.User;

@Mapper
public interface UsersMapper {
    User findUserById(String id);
}
