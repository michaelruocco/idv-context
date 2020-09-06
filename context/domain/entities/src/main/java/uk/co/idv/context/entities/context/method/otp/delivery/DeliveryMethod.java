package uk.co.idv.context.entities.context.method.otp.delivery;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import uk.co.idv.context.entities.context.eligibility.Eligibility;

import java.util.UUID;

@Data
@Builder
public class DeliveryMethod {

    private final UUID id;
    private final String type;
    private final String value;

    @With
    private final Eligibility eligibility;

}
