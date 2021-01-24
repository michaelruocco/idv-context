package uk.co.idv.context.adapter.json.policy.sequence.stage;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.context.entities.policy.sequence.stage.AllMethodsStageTypeMother;
import uk.co.idv.context.entities.policy.sequence.stage.AnyMethodsStageTypeMother;
import uk.co.idv.context.entities.policy.sequence.stage.StagePoliciesMother;

import java.util.stream.Stream;

public class StagePoliciesArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(AllMethodsStagePoliciesJsonMother.build(), StagePoliciesMother.with(AllMethodsStageTypeMother.build())),
                Arguments.of(AnyMethodsStagePoliciesJsonMother.build(), StagePoliciesMother.with(AnyMethodsStageTypeMother.build()))
        );
    }

}
