package uk.co.idv.context.usecases.context.event.expiry;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.context.Context;

@Slf4j
@RequiredArgsConstructor
@Data
public class ExpiryHandler implements Runnable {

    private final Context context;

    @Override
    public void run() {
        log.info("context {} expired", context.getId());
    }

}
