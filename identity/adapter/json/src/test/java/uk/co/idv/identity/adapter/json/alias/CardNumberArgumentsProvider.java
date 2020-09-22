package uk.co.idv.identity.adapter.json.alias;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.identity.entities.alias.CreditCardNumberMother;
import uk.co.idv.identity.entities.alias.DebitCardNumberMother;

import java.util.stream.Stream;

public class CardNumberArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(CreditCardNumberJsonMother.creditCardNumber(), CreditCardNumberMother.creditCardNumber()),
                Arguments.of(DebitCardNumberJsonMother.debitCardNumber(), DebitCardNumberMother.debitCardNumber())
        );
    }

}
