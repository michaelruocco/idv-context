package uk.co.idv.context.adapter.verification.client.logging;

import uk.org.webcompere.systemstubs.ThrowingRunnable;

import java.util.Collection;
import java.util.stream.Collectors;

import static uk.org.webcompere.systemstubs.SystemStubs.tapSystemOut;

public class LogOutputUtils {

    private LogOutputUtils() {
        // utility class
    }

    public static Collection<String> captureLogLines(ThrowingRunnable statement) throws Exception {
        return tapSystemOut(statement)
                .lines()
                .collect(Collectors.toList());
    }

}
