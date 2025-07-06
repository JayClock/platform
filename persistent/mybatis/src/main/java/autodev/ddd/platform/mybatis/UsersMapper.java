package autodev.ddd.platform.mybatis;

import org.apache.ibatis.annotations.Mapper;
import autodev.ddd.platform.model.User;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UsersMapper {
    User findUserById(@Param("id") String id);
}
