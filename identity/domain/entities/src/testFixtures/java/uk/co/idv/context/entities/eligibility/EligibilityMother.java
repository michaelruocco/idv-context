package uk.co.idv.context.entities.eligibility;

import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.DefaultAliases;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.entities.identity.RequestedData;
import uk.co.idv.context.entities.identity.RequestedDataMother;


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
