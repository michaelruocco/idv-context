package uk.co.idv.context.entities.context;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.context.method.Methods;

import java.util.UUID;

@Builder
@Data
public class EligibleMethodsContext {

    private final UUID id;
    private final Activity activity;
    private final Methods methods;
    private final boolean protectSensitiveData;

}
