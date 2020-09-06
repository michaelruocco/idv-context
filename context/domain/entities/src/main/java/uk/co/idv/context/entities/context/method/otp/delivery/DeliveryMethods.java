package uk.co.idv.context.entities.context.method.otp.delivery;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
public class DeliveryMethods implements Iterable<DeliveryMethod> {

    private final Collection<DeliveryMethod> values;

    public DeliveryMethods(DeliveryMethod... values) {
        this(Arrays.asList(values));
    }

    @Override
    public Iterator<DeliveryMethod> iterator() {
        return values.iterator();
    }

}
