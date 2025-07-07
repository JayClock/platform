package autodev.ddd.platform.api;

import autodev.ddd.platform.api.representation.PurchaseModel;
import autodev.ddd.platform.model.Purchaser;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.springframework.hateoas.CollectionModel;

public class PurchasesApi {
    private final Purchaser purchaser;

    public PurchasesApi(Purchaser purchaser) {
        this.purchaser = purchaser;
    }

    @GET
    public CollectionModel<PurchaseModel> findAll(@Context UriInfo info, @DefaultValue("0") @QueryParam("page") int page) {
        return new Pagination<>(purchaser.purchases().findAll(), 40).page(page,
                purchase -> new PurchaseModel(purchase, info),
                p -> ApiTemplates.purchases(info).queryParam("page", p).build(purchaser.getIdentity())
        );
    }
}


