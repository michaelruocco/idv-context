package uk.co.idv.context.adapter.stub.identity.find.data.alias;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.find.data.AliasLoader;

@Slf4j
public class StubAliasLoader implements AliasLoader {

    @Override
    public Aliases load(FindIdentityRequest request) {
        if (shouldReturnStubbedData(request.getAliases())) {
            return loadStubbedData();
        }
        return loadEmptyData();
    }

    private boolean shouldReturnStubbedData(Aliases aliases) {
        return aliases.stream().noneMatch(this::shouldReturnStubbedData);
    }

    private boolean shouldReturnStubbedData(Alias alias) {
        return alias.getValue().endsWith("9");
    }

    private Aliases loadStubbedData() {
        return AliasesMother.idvIdAndCreditCardNumber();
    }

    private Aliases loadEmptyData() {
        return AliasesMother.empty();
    }

}
