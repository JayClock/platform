package autodev.ddd.platform;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.Map;

@Mapper
public interface TestDataMapper {
    @Insert("INSERT INTO USERS(id, name, email) VALUES(#{id}, #{name}, #{email})")
    void insertUser(@Param("id") String id, @Param("name") String name, @Param("email") String email);
}
