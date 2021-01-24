package uk.co.idv.context.entities.context.sequence.stage;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.MethodVerifications;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Data
public class Stages implements Iterable<Stage> {

    private final Collection<Stage> values;

    public Stages(Stage... stages) {
        this(Arrays.asList(stages));
    }

    @Override
    public Iterator<Stage> iterator() {
        return values.iterator();
    }

    public Stream<Stage> stream() {
        return values.stream();
    }

    public boolean allEligible() {
        return values.stream().allMatch(Stage::isEligible);
    }

    public Collection<String> getIneligibleMethodNames() {
        return values.stream()
                .map(Stage::getIneligibleMethodNames)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public boolean allSuccessful(MethodVerifications verifications) {
        return values.stream().allMatch(stage -> stage.isSuccessful(verifications));
    }

    public boolean allComplete(MethodVerifications verifications) {
        return values.stream().allMatch(stage -> stage.isComplete(verifications));
    }

    public long completedMethodCount(MethodVerifications verifications) {
        return values.stream().mapToLong(stage -> stage.completedMethodCount(verifications)).sum();
    }

    public Methods getNextIncompleteMethods(MethodVerifications verifications) {
        return getNextIncomplete(verifications)
                .map(stage -> stage.getNextMethods(verifications))
                .orElse(new Methods());
    }

    public Optional<Stage> getNextIncomplete(MethodVerifications verifications) {
        return values.stream().filter(stage -> !stage.isComplete(verifications)).findFirst();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public Duration getTotalDuration() {
        return values.stream()
                .map(Stage::getDuration)
                .reduce(Duration.ZERO, Duration::plus);
    }

    public Stages updateMethods(UnaryOperator<Method> function) {
        return new Stages(values.stream()
                .map(stage -> stage.updateMethods(function))
                .collect(Collectors.toList())
        );
    }

}
