package uk.co.idv.context.usecases.identity.service.find.data;

import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.alias.Aliases;

import java.time.Duration;

@Builder
@Data
public class AsyncDataLoadRequest {

    private final Duration timeout;
    private final Aliases aliases;

}