package uk.co.idv.identity.adapter.eligibility.external.data.alias;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.identity.entities.alias.DebitCardNumberMother;
import uk.co.idv.identity.entities.identity.FindIdentityRequest;
import uk.co.idv.identity.usecases.eligibility.external.data.AliasLoader;

@Slf4j
public class StubAliasLoader implements AliasLoader {

    @Override
    public Aliases load(FindIdentityRequest request) {
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
