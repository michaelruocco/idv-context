package uk.co.idv.context.usecases.context.event.expiry;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import uk.co.idv.context.entities.context.Context;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Data
public class ExpiryHandler implements Runnable {

    private final Map<String, String> mdc = MDC.getCopyOfContextMap();
    private final Context context;

    @Override
    public void run() {
        try {
            MDC.setContextMap(mdc);
            log.info("context {} expired", context.getId());
        } finally {
            MDC.clear();
        }
    }

}
