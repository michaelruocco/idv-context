package uk.co.idv.context.adapter.identity.find.external.data.alias;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.identity.find.external.StubDataLoadPolicy;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.usecases.identity.find.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.find.data.AliasLoader;

@Slf4j
public class StubAliasLoader implements AliasLoader {

    @Override
    public Aliases load(FindIdentityRequest request) {
        if (StubDataLoadPolicy.shouldLoad(request.getAliases())) {
            return loadStubbedData();
        }
        return loadEmptyData();
    }

    private Aliases loadStubbedData() {
        return AliasesMother.idvIdAndCreditCardNumber();
    }

    private Aliases loadEmptyData() {
        return AliasesMother.empty();
    }

}
