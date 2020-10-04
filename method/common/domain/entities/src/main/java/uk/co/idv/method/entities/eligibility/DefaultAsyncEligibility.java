package uk.co.idv.method.entities.eligibility;

import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Builder
@Data
public class DefaultAsyncEligibility implements AsyncEligibility {

    private final boolean complete;
    private final boolean eligible;
    private final String reason;

    @Override
    public Optional<String> getReason() {
        return Optional.ofNullable(reason);
    }

}
