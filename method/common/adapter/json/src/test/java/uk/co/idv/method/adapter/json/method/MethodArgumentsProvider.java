package uk.co.idv.method.adapter.json.method;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.method.adapter.json.fake.FakeMethodJsonMother;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;

import java.util.stream.Stream;

public class MethodArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(FakeMethodJsonMother.build(), FakeMethodMother.build())
        );
    }

}
