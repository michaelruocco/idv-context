package uk.co.idv.context.entities.activity;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class Login implements Activity {

    public static final String NAME = "login";

    private final Instant timestamp;
    private final String system;

    @Override
    public String getName() {
        return NAME;
    }

    public String getSystem() {
        return system;
    }

}
