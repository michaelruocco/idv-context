package uk.co.idv.identity.entities.mobiledevice;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ToString
@EqualsAndHashCode
public class MobileDevices implements Iterable<MobileDevice> {

    private final Collection<MobileDevice> values;

    public MobileDevices(MobileDevice... values) {
        this(Arrays.asList(values));
    }

    public MobileDevices(Collection<MobileDevice> values) {
        this.values = new LinkedHashSet<>(values);
    }

    public Iterator<MobileDevice> iterator() {
        return values.iterator();
    }

    public Stream<MobileDevice> stream() {
        return values.stream();
    }

    public Collection<String> getTokens() {
        return stream()
                .map(MobileDevice::getToken)
                .collect(Collectors.toList());
    }

    public MobileDevices add(MobileDevices others) {
        Collection<MobileDevice> mergedValues = new LinkedHashSet<>();
        mergedValues.addAll(this.values);
        mergedValues.addAll(others.values);
        return new MobileDevices(mergedValues);
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

}
