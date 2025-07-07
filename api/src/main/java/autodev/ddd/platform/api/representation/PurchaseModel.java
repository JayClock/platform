package autodev.ddd.platform.api.representation;

import autodev.ddd.platform.description.PurchaseDescription;
import autodev.ddd.platform.model.Purchase;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.ws.rs.core.UriInfo;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "purchases")
public class PurchaseModel extends RepresentationModel<PurchaseModel> {
    @JsonProperty
    public String id;
    @JsonUnwrapped
    public PurchaseDescription description;

    public PurchaseModel(Purchase purchase, UriInfo uriInfo) {
        this.id = purchase.getIdentity();
        this.description = purchase.getDescription();
    }
}
