package uk.co.idv.common.mongo;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.time.Instant;

import static uk.co.idv.common.usecases.duration.DurationCalculator.millisBetweenNowAnd;

@Slf4j
//TODO split this and duration calculator into separate duration-logging library
public class MongoDurationLogger {

    private static final String OPERATION_NAME = "mongo-operation";
    private static final String DURATION_NAME = "mongo-duration";

    private MongoDurationLogger() {
        // utility class
    }

    public static void log(String operation, Instant start) {
        long duration = millisBetweenNowAnd(start);
        MDC.put(OPERATION_NAME, operation);
        MDC.put(DURATION_NAME, Long.toString(duration));
        log.info("{} took {}ms", operation, duration);
        MDC.remove(OPERATION_NAME);
        MDC.remove(DURATION_NAME);
    }

}
