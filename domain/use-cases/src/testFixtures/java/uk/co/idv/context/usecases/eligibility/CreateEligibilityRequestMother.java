package uk.co.idv.context.usecases.eligibility;

import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;
import uk.co.idv.context.entities.eligibility.EligibilityMother;
import uk.co.idv.context.usecases.eligibility.CreateEligibilityRequest.CreateEligibilityRequestBuilder;

import java.util.Collection;

public interface CreateEligibilityRequestMother {

    static CreateEligibilityRequest withChannel(Channel channel) {
        return builder().channel(channel).build();
    }

    static CreateEligibilityRequest withAliases(Aliases aliases) {
        return builder().aliases(aliases).build();
    }

    static CreateEligibilityRequest withRequested(Collection<String> requested) {
        return builder().requested(requested).build();
    }

    static CreateEligibilityRequest build() {
        return builder().build();
    }

    static CreateEligibilityRequestBuilder builder() {
        return CreateEligibilityRequest.builder()
                .aliases(AliasesMother.creditCardNumberOnly())
                .channel(DefaultChannelMother.build())
                .requested(allRequested());
    }

    static Collection<String> allRequested() {
        return EligibilityMother.allRequested();
    }

}
