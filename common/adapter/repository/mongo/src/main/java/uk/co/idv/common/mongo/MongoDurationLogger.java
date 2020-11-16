package uk.co.idv.common.mongo;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.time.Instant;

import static uk.co.idv.common.usecases.duration.DurationCalculator.millisBetweenNowAnd;

@Slf4j
public class MongoDurationLogger {

    private MongoDurationLogger() {
        // utility class
    }

    public static void log(String operation, Instant start) {
        long duration = millisBetweenNowAnd(start);
        MDC.put("mongo-operation", operation);
        MDC.put("mongo-duration", Long.toString(duration));
        log.debug("{} took {}ms", operation, duration);
        MDC.remove("mongo-operation");
        MDC.remove("mongo-duration");
    }

}
