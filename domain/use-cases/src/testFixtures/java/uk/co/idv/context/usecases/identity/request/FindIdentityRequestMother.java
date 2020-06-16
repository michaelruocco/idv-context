package uk.co.idv.context.usecases.identity.request;

import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.usecases.identity.find.DefaultFindIdentityRequest;
import uk.co.idv.context.usecases.identity.find.FindIdentityRequest;
import uk.co.idv.context.usecases.identity.find.DefaultFindIdentityRequest.DefaultFindIdentityRequestBuilder;

public interface FindIdentityRequestMother {

    static FindIdentityRequest withAliases(Alias... aliases) {
        return builder().aliases(AliasesMother.with(aliases)).build();
    }

    static FindIdentityRequest withAliases(Aliases aliases) {
        return builder().aliases(aliases).build();
    }

    static FindIdentityRequest build() {
        return builder().build();
    }

    static DefaultFindIdentityRequestBuilder builder() {
        return DefaultFindIdentityRequest.builder()
                .aliases(AliasesMother.creditCardNumberOnly());
    }

}
