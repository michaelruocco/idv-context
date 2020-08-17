package uk.co.idv.context.entities.lockout;

import uk.co.idv.context.entities.alias.DefaultAliasMother;
import uk.co.idv.context.entities.lockout.DefaultExternalLockoutRequest.DefaultExternalLockoutRequestBuilder;

public interface ExternalLockoutRequestMother {

    static ExternalLockoutRequest build() {
        return builder().build();
    }

    static DefaultExternalLockoutRequestBuilder builder() {
        return DefaultExternalLockoutRequest.builder()
                .channelId("default-channel")
                .activityName("default-activity")
                .alias(DefaultAliasMother.build());
    }

}
