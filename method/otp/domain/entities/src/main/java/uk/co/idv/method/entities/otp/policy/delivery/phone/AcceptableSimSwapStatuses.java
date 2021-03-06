package uk.co.idv.method.entities.otp.policy.delivery.phone;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
@Data
public class AcceptableSimSwapStatuses implements Iterable<String> {

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    public static final String TIMEOUT = "timeout";
    public static final String UNKNOWN = "unknown";

    @Getter(AccessLevel.NONE)
    private final Collection<String> values;

    public AcceptableSimSwapStatuses(String... values) {
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
