package autodev.ddd.platform.mybatis.associations;

import autodev.ddd.mybatis.database.EntityList;
import autodev.ddd.platform.model.Purchase;
import autodev.ddd.platform.model.Purchaser;
import autodev.ddd.platform.mybatis.PurchasesMapper;
import jakarta.inject.Inject;

import java.util.List;

public class PurchaserPurchases extends EntityList<String, Purchase> implements Purchaser.Purchases {
    private String purchaserId;

    @Inject
    private PurchasesMapper purchasesMapper;

    @Override
    protected List<Purchase> findEntities(int from, int to) {
        return purchasesMapper.findPurchasesByPurchaserId(purchaserId, from, to - from);
    }

    @Override
    protected Purchase findEntity(String id) {
        return this.purchasesMapper.findPurchaseByPurchaserIdAndId(purchaserId, id);
    }

    @Override
    public int size() {
        return purchasesMapper.countPurchasesByPurchaserId(purchaserId);
    }
}
