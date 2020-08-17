package uk.co.idv.context.entities.lockout;

import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.DefaultAliasMother;
import uk.co.idv.context.entities.lockout.DefaultExternalLockoutRequest.DefaultExternalLockoutRequestBuilder;

public interface DefaultExternalLockoutRequestMother {

    static DefaultExternalLockoutRequest withAlias(Alias alias) {
        return builder().alias(alias).build();
    }

    static DefaultExternalLockoutRequest build() {
        return builder().build();
    }

    static DefaultExternalLockoutRequestBuilder builder() {
        return DefaultExternalLockoutRequest.builder()
                .channelId("default-channel")
                .activityName("default-activity")
                .alias(DefaultAliasMother.build());
    }

}
