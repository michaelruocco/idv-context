package uk.co.idv.context.adapter.client.logging;

import com.github.stefanbirkner.systemlambda.Statement;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemOut;

public class LogOutputUtils {

    private LogOutputUtils() {
        // utility class
    }

    public static Collection<String> captureLogLines(Statement statement) throws Exception {
        String[] logLines = tapSystemOut(statement).split(System.lineSeparator());
        return Arrays.stream(logLines).collect(Collectors.toList());
    }

}
