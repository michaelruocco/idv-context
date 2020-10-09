package uk.co.idv.method.adapter.json.fake;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;
import uk.co.idv.method.entities.result.ResultMother;

import java.util.stream.Stream;

public class FakeMethodArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(FakeMethodJsonMother.build(), FakeMethodMother.build()),
                Arguments.of(FakeMethodJsonMother.withResults(), FakeMethodMother.withResult(ResultMother.withMethodName("fake-method")))
        );
    }

}
