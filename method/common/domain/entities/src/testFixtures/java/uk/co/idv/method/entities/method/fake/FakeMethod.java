package uk.co.idv.method.entities.method.fake;

import lombok.Builder;
import lombok.Data;
import lombok.With;
import uk.co.idv.method.entities.eligibility.Eligibility;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodConfig;

@Builder(toBuilder = true)
@Data
public class FakeMethod implements Method {

    @With
    private final String name;
    private final Eligibility eligibility;
    private final MethodConfig config;

}
