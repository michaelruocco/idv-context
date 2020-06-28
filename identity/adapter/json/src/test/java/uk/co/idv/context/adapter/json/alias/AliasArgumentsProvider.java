package uk.co.idv.context.adapter.json.alias;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.CreditCardNumberMother;
import uk.co.idv.context.entities.alias.DebitCardNumberMother;
import uk.co.idv.context.entities.alias.DefaultAliasMother;
import uk.co.idv.context.entities.alias.IdvIdMother;

import java.util.stream.Stream;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public class AliasArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                args("alias/default-alias.json", DefaultAliasMother.build()),
                args("alias/idv-id.json", IdvIdMother.idvId()),
                args("alias/credit-card-number.json", CreditCardNumberMother.creditCardNumber()),
                args("alias/debit-card-number.json", DebitCardNumberMother.debitCardNumber())
        );
    }

    private static Arguments args(String path, Alias alias) {
        return Arguments.of(loadContentFromClasspath(path), alias);
    }

}
