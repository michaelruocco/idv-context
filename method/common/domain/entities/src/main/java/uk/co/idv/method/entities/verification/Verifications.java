package uk.co.idv.method.entities.verification;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.method.MethodVerifications;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@RequiredArgsConstructor
public class Verifications implements MethodVerifications, Iterable<Verification> {

    private final Collection<Verification> values;

    public Verifications(Verification... values) {
        this(Arrays.asList(values));
    }

    @Override
    public long countByName(String methodName) {
        return values.stream()
                .filter(verification -> verification.hasMethodName(methodName))
                .count();
    }

    @Override
    public boolean containsSuccessful(String methodName) {
        return values.stream()
                .filter(verification -> verification.hasMethodName(methodName))
                .anyMatch(Verification::isSuccessful);
    }

    @Override
    public Iterator<Verification> iterator() {
        return values.iterator();
    }

    public Verification getById(UUID id) {
        return values.stream()
                .filter(verification -> verification.hasId(id))
                .findFirst()
                .orElseThrow(() -> new VerificationNotFoundException(id));
    }

    public Verifications add(Verification verification) {
        Collection<Verification> updated = new ArrayList<>(values);
        updated.add(verification);
        return new Verifications(updated);
    }

    public Verifications complete(CompleteVerificationRequest request) {
        Verification verification = getById(request.getId());
        Map<UUID, Verification> valuesById = values.stream()
                .collect(Collectors.toMap(Verification::getId, Function.identity()));
        valuesById.put(verification.getId(), verification.complete(request));
        return new Verifications(valuesById.values());
    }

}
