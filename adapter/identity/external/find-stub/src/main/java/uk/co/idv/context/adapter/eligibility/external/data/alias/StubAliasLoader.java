package uk.co.idv.context.adapter.eligibility.external.data.alias;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.DebitCardNumberMother;
import uk.co.idv.context.usecases.eligibility.external.ExternalFindIdentityRequest;
import uk.co.idv.context.usecases.eligibility.external.data.AliasLoader;

@Slf4j
public class StubAliasLoader implements AliasLoader {

    @Override
    public Aliases load(ExternalFindIdentityRequest request) {
        Aliases aliases = request.getAliases();
        if (!aliases.hasAnyValuesEndingWith("9")) {
            return loadStubbedData(aliases);
        }
        return loadEmptyData();
    }

    private Aliases loadStubbedData(Aliases aliases) {
        Aliases creditCardNumbers = aliases.getCreditCardNumbers();
        if (!creditCardNumbers.isEmpty()) {
            String value = creditCardNumbers.getFirstValue();
            String incrementedValue = FirstDigitIncrementer.incrementFirstDigit(value);
            return aliases.add(DebitCardNumberMother.withValue(incrementedValue));
        }
        return aliases;
    }

    private Aliases loadEmptyData() {
        return AliasesMother.empty();
    }

}
