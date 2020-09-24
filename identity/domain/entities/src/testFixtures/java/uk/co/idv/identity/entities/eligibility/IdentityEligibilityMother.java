package uk.co.idv.identity.entities.eligibility;

import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.identity.entities.alias.DefaultAliases;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.channel.DefaultChannelMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.identity.entities.identity.RequestedDataMother;


public interface IdentityEligibilityMother {

    static IdentityEligibility withAliases(Alias... aliases) {
        return builder().aliases(AliasesMother.with(aliases)).build();
    }

    static IdentityEligibility withAliases(DefaultAliases aliases) {
        return builder().aliases(aliases).build();
    }

    static IdentityEligibility withChannel(Channel channel) {
        return builder().channel(channel).build();
    }

    static IdentityEligibility withRequestedData(RequestedData requestedData) {
        return builder().requestedData(requestedData).build();
    }

    static IdentityEligibility withIdentity(Identity identity) {
        return builder().identity(identity).build();
    }

    static IdentityEligibility build() {
        return builder().build();
    }

    static IdentityEligibility.IdentityEligibilityBuilder builder() {
        return IdentityEligibility.builder()
                .aliases(AliasesMother.creditCardNumberOnly())
                .channel(DefaultChannelMother.build())
                .requestedData(RequestedDataMother.allRequested())
                .identity(IdentityMother.example());
    }

}
