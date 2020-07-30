package uk.co.idv.context.adapter.json.policy.key;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.policy.key.channel.ChannelPolicyKeyDeserializer;
import uk.co.idv.context.adapter.json.policy.key.channel.ChannelPolicyKeyMixin;
import uk.co.idv.context.entities.policy.PolicyKey;
import uk.co.idv.context.entities.policy.key.ChannelPolicyKey;

public class PolicyKeyModule extends SimpleModule {

    public PolicyKeyModule() {
        super("policy-key-module", Version.unknownVersion());

        setUpDefaults();
        setUpChannelPolicyKey();
    }

    private void setUpDefaults() {
        addDeserializer(PolicyKey.class, new PolicyKeyDeserializer());
    }

    private void setUpChannelPolicyKey() {
        setMixInAnnotation(ChannelPolicyKey.class, ChannelPolicyKeyMixin.class);
        addDeserializer(ChannelPolicyKey.class, new ChannelPolicyKeyDeserializer());
    }

}
