package uk.co.idv.context.entities.context.sequence;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.context.method.Methods;

@Builder
@Data
public class Sequence {

    private final String name;
    private final Methods methods;

}
