package uk.co.idv.policy.adapter.json.key;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.policy.adapter.json.key.channel.ChannelPolicyKeyJsonMother;
import uk.co.idv.policy.adapter.json.key.channelactivity.ChannelActivityPolicyKeyJsonMother;
import uk.co.idv.policy.adapter.json.key.channelactivityalias.ChannelActivityAliasPolicyKeyJsonMother;
import uk.co.idv.policy.adapter.json.key.validcookie.ValidCookiePolicyKeyJsonMother;
import uk.co.idv.policy.entities.policy.key.channelactivityalias.ChannelActivityAliasPolicyKeyMother;
import uk.co.idv.policy.entities.policy.key.channelactivity.ChannelActivityPolicyKeyMother;
import uk.co.idv.policy.entities.policy.key.channel.ChannelPolicyKeyMother;
import uk.co.idv.policy.entities.policy.key.validcookie.ValidCookiePolicyKeyMother;

import java.util.stream.Stream;

public class PolicyKeyArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(ChannelPolicyKeyJsonMother.build(), ChannelPolicyKeyMother.build()),
                Arguments.of(ChannelActivityPolicyKeyJsonMother.build(), ChannelActivityPolicyKeyMother.build()),
                Arguments.of(ChannelActivityAliasPolicyKeyJsonMother.build(), ChannelActivityAliasPolicyKeyMother.build()),
                Arguments.of(ValidCookiePolicyKeyJsonMother.build(), ValidCookiePolicyKeyMother.build())
        );
    }

}
