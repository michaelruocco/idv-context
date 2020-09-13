package uk.co.idv.context.entities.policy.method.otp.delivery;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.policy.RequestedDataMerger;
import uk.co.idv.context.entities.policy.RequestedDataProvider;
import uk.co.idv.context.entities.policy.method.otp.delivery.phone.PhoneDeliveryMethodConfig;
import uk.co.idv.identity.entities.identity.RequestedData;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class DeliveryMethodConfigs implements Iterable<DeliveryMethodConfig>, RequestedDataProvider {

    private final Collection<DeliveryMethodConfig> values;

    public DeliveryMethodConfigs(DeliveryMethodConfig... values) {
        this(Arrays.asList(values));
    }

    @Override
    public RequestedData getRequestedData() {
        return RequestedDataMerger.mergeRequestedData(values);
    }

    @Override
    public Iterator<DeliveryMethodConfig> iterator() {
        return values.iterator();
    }

    public Stream<DeliveryMethodConfig> stream() {
        return values.stream();
    }

    public Optional<Duration> getShortestSimSwapConfigTimeout() {
        return values.stream()
                .filter(config -> config instanceof PhoneDeliveryMethodConfig)
                .map(config -> (PhoneDeliveryMethodConfig) config)
                .map(PhoneDeliveryMethodConfig::getSimSwapTimeout)
                .flatMap(Optional::stream)
                .min(Comparator.comparingLong(Duration::toMillis));
    }

}
