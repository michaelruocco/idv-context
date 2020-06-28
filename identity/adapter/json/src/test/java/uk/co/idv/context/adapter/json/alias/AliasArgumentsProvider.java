package uk.co.idv.context.adapter.json.alias;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.context.entities.alias.CreditCardNumberMother;
import uk.co.idv.context.entities.alias.DebitCardNumberMother;
import uk.co.idv.context.entities.alias.DefaultAliasMother;
import uk.co.idv.context.entities.alias.IdvIdMother;

import java.util.stream.Stream;

public class AliasArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(DefaultAliasJsonMother.build(), DefaultAliasMother.build()),
                Arguments.of(IdvIdJsonMother.idvId(), IdvIdMother.idvId()),
                Arguments.of(CreditCardNumberJsonMother.creditCardNumber(), CreditCardNumberMother.creditCardNumber()),
                Arguments.of(DebitCardNumberJsonMother.debitCardNumber(), DebitCardNumberMother.debitCardNumber())
        );
    }

}
