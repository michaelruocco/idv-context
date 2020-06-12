package uk.co.idv.context.adapter.stub.identity.data.alias;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.stub.identity.data.StubDataFactory;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;


@Slf4j
public class StubAliasFactory implements StubDataFactory<Aliases> {

    @Override
    public String getName() {
        return Aliases.class.getSimpleName();
    }

    @Override
    public Aliases getPopulatedData() {
        return AliasesMother.idvIdAndCreditCardNumber();
    }

    @Override
    public Aliases getEmptyData() {
        return AliasesMother.empty();
    }

}
