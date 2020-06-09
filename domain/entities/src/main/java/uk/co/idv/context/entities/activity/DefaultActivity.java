package uk.co.idv.context.entities.activity;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Builder
@Data
public class DefaultActivity implements Activity {

    private final String name;
    private final Instant timestamp;

}
