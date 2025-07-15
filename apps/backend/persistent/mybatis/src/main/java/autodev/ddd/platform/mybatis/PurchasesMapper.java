package autodev.ddd.platform.mybatis;

import autodev.ddd.platform.model.Purchase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PurchasesMapper {
    int countPurchasesByPurchaserId(String purchaserId);

    Purchase findPurchaseByPurchaserIdAndId(@Param("purchaser_id") String purchaserId, @Param("id") String id);

    List<Purchase> findPurchasesByPurchaserId(@Param("purchaser_id") String purchaserId, @Param("from") int from, @Param("size") int size);
}
