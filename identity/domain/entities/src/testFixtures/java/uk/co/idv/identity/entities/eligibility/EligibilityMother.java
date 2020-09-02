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


public interface EligibilityMother {

    static Eligibility withAliases(Alias... aliases) {
        return builder().aliases(AliasesMother.with(aliases)).build();
    }

    static Eligibility withAliases(DefaultAliases aliases) {
        return builder().aliases(aliases).build();
    }

    static Eligibility withChannel(Channel channel) {
        return builder().channel(channel).build();
    }

    static Eligibility withRequestedData(RequestedData requestedData) {
        return builder().requestedData(requestedData).build();
    }

    static Eligibility withIdentity(Identity identity) {
        return builder().identity(identity).build();
    }

    static Eligibility build() {
        return builder().build();
    }

    static Eligibility.EligibilityBuilder builder() {
        return Eligibility.builder()
                .aliases(AliasesMother.creditCardNumberOnly())
                .channel(DefaultChannelMother.build())
                .requestedData(RequestedDataMother.allRequested())
                .identity(IdentityMother.example());
    }

}
