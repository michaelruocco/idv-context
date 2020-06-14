package uk.co.idv.context.adapter.stub.identity.find.data.alias;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.stub.identity.find.StubDataLoadPolicy;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;
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
