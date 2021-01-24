package uk.co.idv.context.adapter.json.policy.sequence.stage.type;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.context.entities.policy.sequence.stage.AllMethodsStageTypeMother;
import uk.co.idv.context.entities.policy.sequence.stage.AnyMethodsStageTypeMother;

import java.util.stream.Stream;

public class StageTypeArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(AllMethodsStageTypeJsonMother.build(), AllMethodsStageTypeMother.build()),
                Arguments.of(AnyMethodsStageTypeJsonMother.build(), AnyMethodsStageTypeMother.build())
        );
    }

}
