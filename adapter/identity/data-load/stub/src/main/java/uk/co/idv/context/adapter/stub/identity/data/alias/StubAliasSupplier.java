package uk.co.idv.context.adapter.stub.identity.data.alias;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.stub.identity.data.Delay;
import uk.co.idv.context.adapter.stub.identity.data.StubDataSupplier;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.usecases.identity.FindIdentityRequest;

@Slf4j
public class StubAliasSupplier extends StubDataSupplier<Aliases> {

    public StubAliasSupplier(FindIdentityRequest request, Delay delay) {
        super(request, delay, new StubAliasFactory());
    }

}
