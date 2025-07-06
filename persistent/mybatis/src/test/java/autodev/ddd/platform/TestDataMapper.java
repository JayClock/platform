package autodev.ddd.platform;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;

@Mapper
public interface TestDataMapper {
    @Insert("INSERT INTO USERS(id, name, email) VALUES(#{id}, #{name}, #{email})")
    void insertUser(@Param("id") String id, @Param("name") String name, @Param("email") String email);

    @Insert("INSERT INTO PURCHASERS(user_id, amount) VALUES ( #{user_id},#{amount} )")
    void insertPurchaser(@Param("user_id") String user_id, @Param("amount") BigDecimal amount);

    @Insert("INSERT INTO PURCHASES(id, purchaser_id, created_at) VALUES ( #{id} ,#{purchaser_id},#{created_at} )")
    void insertPurchase(@Param("id") String id, @Param("purchaser_id") String purchaser_id, @Param("created_at") Date created_at);
}
