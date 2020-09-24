package uk.co.idv.context.entities.context.eligibility;

import lombok.Data;

import java.util.Optional;

@Data
public class Ineligible implements Eligibility {

    private final String reason;

    public Ineligible(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean isEligible() {
        return false;
    }

    @Override
    public Optional<String> getReason() {
        return Optional.of(reason);
    }

}
