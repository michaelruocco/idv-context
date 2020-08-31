package uk.co.idv.context.entities.policy.method.otp.delivery.phone.simswap;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
public class AcceptableSimSwapResults implements Iterable<String> {

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    public static final String TIMEOUT = "timeout";
    public static final String UNKNOWN = "unknown";

    private final Collection<String> values;

    public AcceptableSimSwapResults(String... values) {
        this(Arrays.asList(values));
    }

    @Override
    public Iterator<String> iterator() {
        return values.iterator();
    }

    public boolean isAcceptable(String result) {
        return values.contains(result);
    }

}
