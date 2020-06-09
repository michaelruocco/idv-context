package uk.co.idv.context.entities.activity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
@Data
public class Login implements Activity {

    public static final String NAME = "login";

    private final Instant timestamp;

    @Override
    public String getName() {
        return NAME;
    }

}
