package autodev.ddd.platform.api.representation;

import autodev.ddd.platform.api.ApiTemplates;
import autodev.ddd.platform.description.PurchaserDescription;
import autodev.ddd.platform.model.Purchaser;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.ws.rs.core.UriInfo;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

public class PurchaserModel extends RepresentationModel<PurchaserModel> {
    @JsonProperty
    private String id;
    @JsonUnwrapped
    private PurchaserDescription description;

    public PurchaserModel(Purchaser purchaser, UriInfo uriInfo) {
        this.id = purchaser.getIdentity();
        this.description = purchaser.getDescription();
        add(Link.of(ApiTemplates.purchaser(uriInfo).build(purchaser.getIdentity()).getPath(), "self"));
        add(Link.of(ApiTemplates.purchases(uriInfo).build(purchaser.getIdentity()).getPath(), "purchases"));
    }
}
