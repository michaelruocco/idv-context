package uk.co.idv.lockout.entities;

import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.lockout.entities.DefaultExternalLockoutRequest.DefaultExternalLockoutRequestBuilder;

public interface DefaultExternalLockoutRequestMother {

    static DefaultExternalLockoutRequest withAlias(Alias alias) {
        return withAliases(AliasesMother.with(alias));
    }

    static DefaultExternalLockoutRequest withAliases(Aliases aliases) {
        return builder().aliases(aliases).build();
    }

    static DefaultExternalLockoutRequest build() {
        return builder().build();
    }

    static DefaultExternalLockoutRequestBuilder builder() {
        return DefaultExternalLockoutRequest.builder()
                .channelId("default-channel")
                .activityName("default-activity")
                .aliases(AliasesMother.defaultAliasOnly());
    }

}
