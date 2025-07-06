package autodev.ddd.platform.mybatis;

import autodev.ddd.platform.model.Purchaser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

@Mapper
public interface PurchasersMapper {
    int createPurchaser(@Param("user_id") String userId, @Param("amount") BigDecimal amount);

    Purchaser findPurchaserByUserId(@Param("user_id") String userId);
}
