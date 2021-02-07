package uk.co.idv.identity.adapter.json.alias;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.identity.entities.alias.CardNumberMother;
import uk.co.idv.identity.entities.alias.DefaultAliasMother;
import uk.co.idv.identity.entities.alias.IdvIdMother;

import java.util.stream.Stream;

public class AliasArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(DefaultAliasJsonMother.build(), DefaultAliasMother.build()),
                Arguments.of(IdvIdJsonMother.idvId(), IdvIdMother.idvId()),
                Arguments.of(CardNumberJsonMother.credit(), CardNumberMother.credit()),
                Arguments.of(CardNumberJsonMother.debit(), CardNumberMother.debit())
        );
    }

}
