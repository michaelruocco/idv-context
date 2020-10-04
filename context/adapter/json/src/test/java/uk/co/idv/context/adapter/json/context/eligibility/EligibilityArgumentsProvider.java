package uk.co.idv.context.adapter.json.context.eligibility;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.method.entities.eligibility.DefaultAsyncEligibilityMother;
import uk.co.idv.method.entities.eligibility.EligibilityMother;

import java.util.stream.Stream;

public class EligibilityArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(EligibilityJsonMother.eligible(), EligibilityMother.eligible()),
                Arguments.of(EligibilityJsonMother.ineligible(), EligibilityMother.ineligible()),
                Arguments.of(DefaultAsyncEligibilityJsonMother.eligible(), DefaultAsyncEligibilityMother.eligible()),
                Arguments.of(DefaultAsyncEligibilityJsonMother.ineligible(), DefaultAsyncEligibilityMother.ineligible())
        );
    }

}
