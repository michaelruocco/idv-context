package uk.co.idv.identity.usecases.eligibility.external.data;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.identity.entities.alias.Aliases;

import java.time.Duration;

@Builder
@Data
public class AsyncDataLoadRequest {

    private final Duration timeout;
    private final Aliases aliases;

}
