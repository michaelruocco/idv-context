package uk.co.idv.context.adapter.method.otp.delivery.phone.simswap;

import org.apache.commons.math3.util.Pair;
import uk.co.idv.common.entities.async.Delay;
import uk.co.idv.common.entities.async.WeightedRandomDelaySupplier;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;

public class StubSimSwapDelaySupplier extends WeightedRandomDelaySupplier {

    public StubSimSwapDelaySupplier() {
        super(buildWeightedDelays());
    }

    private static Collection<Pair<Delay, Double>> buildWeightedDelays() {
        return Arrays.asList(
                new Pair<>(new Delay(Duration.ofMillis(500)), 0.1),
                new Pair<>(new Delay(Duration.ofMillis(1500)), 0.2),
                new Pair<>(new Delay(Duration.ofSeconds(3)), 0.5),
                new Pair<>(new Delay(Duration.ofSeconds(5)), 0.2),
                new Pair<>(new Delay(Duration.ofSeconds(8)), 0.1)
        );
    }
}
