package uk.co.idv.activity.adapter.json;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.activity.entities.DefaultActivityMother;
import uk.co.idv.activity.entities.LoginMother;
import uk.co.idv.activity.entities.OnlinePurchaseMother;

import java.util.stream.Stream;

public class ActivityArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(DefaultActivityJsonMother.build(), DefaultActivityMother.build()),
                Arguments.of(LoginJsonMother.build(), LoginMother.login()),
                Arguments.of(OnlinePurchaseJsonMother.build(), OnlinePurchaseMother.build())
        );
    }

}
