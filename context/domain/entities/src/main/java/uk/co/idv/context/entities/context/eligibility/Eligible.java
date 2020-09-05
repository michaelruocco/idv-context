package uk.co.idv.context.entities.context.eligibility;

import lombok.Data;

import java.util.Optional;

@Data
public class Eligible implements Eligibility {

    @Override
    public boolean isEligible() {
        return true;
    }

    @Override
    public Optional<String> getReason() {
        return Optional.empty();
    }

}
