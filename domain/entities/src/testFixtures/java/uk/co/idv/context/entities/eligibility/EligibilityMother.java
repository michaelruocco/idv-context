package uk.co.idv.context.entities.eligibility;

import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;

import java.util.Arrays;
import java.util.Collection;

public interface EligibilityMother {

    static Eligibility withAliases(Alias... aliases) {
        return builder().aliases(AliasesMother.with(aliases)).build();
    }

    static Eligibility withAliases(Aliases aliases) {
        return builder().aliases(aliases).build();
    }

    static Eligibility withChannel(Channel channel) {
        return builder().channel(channel).build();
    }

    static Eligibility withRequested(Collection<String> requested) {
        return builder().requested(requested).build();
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
                .requested(allRequested())
                .identity(IdentityMother.example());
    }

    static Collection<String> allRequested() {
        return Arrays.asList("phone-numbers", "email-addresses");
    }

}
