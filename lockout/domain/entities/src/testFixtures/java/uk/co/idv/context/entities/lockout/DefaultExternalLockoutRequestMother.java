package uk.co.idv.context.entities.lockout;

import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.lockout.DefaultExternalLockoutRequest.DefaultExternalLockoutRequestBuilder;

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
