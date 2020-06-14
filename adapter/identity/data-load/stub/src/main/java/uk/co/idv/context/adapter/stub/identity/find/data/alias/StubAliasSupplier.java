package uk.co.idv.context.adapter.stub.identity.find.data.alias;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.stub.identity.find.data.StubDataSupplier;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.find.data.Delay;

@Slf4j
public class StubAliasSupplier extends StubDataSupplier<Aliases> {

    public StubAliasSupplier(FindIdentityRequest request, Delay delay) {
        super(request, delay, new StubAliasFactory());
    }

}
