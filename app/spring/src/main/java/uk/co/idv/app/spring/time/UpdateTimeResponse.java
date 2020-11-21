package uk.co.idv.app.spring.time;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;
import java.time.Instant;

@Data
@Builder
public class UpdateTimeResponse {

    private Duration offset;
    private Instant time;

}
