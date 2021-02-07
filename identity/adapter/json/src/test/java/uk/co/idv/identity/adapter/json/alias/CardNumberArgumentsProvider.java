package uk.co.idv.identity.adapter.json.alias;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.identity.entities.alias.CardNumberMother;

import java.util.stream.Stream;

public class CardNumberArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(CardNumberJsonMother.credit(), CardNumberMother.credit()),
                Arguments.of(CardNumberJsonMother.debit(), CardNumberMother.debit())
        );
    }

}
