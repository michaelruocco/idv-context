package uk.co.idv.context.adapter.json.policy.key;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.context.adapter.json.policy.key.channel.ChannelPolicyKeyJsonMother;
import uk.co.idv.context.adapter.json.policy.key.channelactivity.ChannelActivityPolicyKeyJsonMother;
import uk.co.idv.context.adapter.json.policy.key.channelactivityalias.ChannelActivityAliasPolicyKeyJsonMother;
import uk.co.idv.context.entities.policy.key.ChannelActivityAliasPolicyKeyMother;
import uk.co.idv.context.entities.policy.key.ChannelActivityPolicyKeyMother;
import uk.co.idv.context.entities.policy.key.ChannelPolicyKeyMother;

import java.util.stream.Stream;

public class PolicyKeyArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(ChannelPolicyKeyJsonMother.build(), ChannelPolicyKeyMother.build()),
                Arguments.of(ChannelActivityPolicyKeyJsonMother.build(), ChannelActivityPolicyKeyMother.build()),
                Arguments.of(ChannelActivityAliasPolicyKeyJsonMother.build(), ChannelActivityAliasPolicyKeyMother.build())
        );
    }

}
