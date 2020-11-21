package uk.co.idv.common.entities.async;

import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class WeightedRandomDelaySupplier implements Supplier<Delay> {

    private final EnumeratedDistribution<Delay> delays;

    public WeightedRandomDelaySupplier(Collection<Pair<Delay, Double>> weightedDelays) {
        this(new EnumeratedDistribution<>(new ArrayList<>(weightedDelays)));
    }

    @Override
    public Delay get() {
        return delays.sample();
    }

}
