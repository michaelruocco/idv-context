package uk.co.idv.policy.adapter.json.key;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.policy.adapter.json.key.channel.ChannelPolicyKeyDeserializer;
import uk.co.idv.policy.adapter.json.key.channel.ChannelPolicyKeyMixin;
import uk.co.idv.policy.adapter.json.key.channelactivity.ChannelActivityPolicyKeyDeserializer;
import uk.co.idv.policy.adapter.json.key.channelactivity.ChannelActivityPolicyKeyMixin;
import uk.co.idv.policy.adapter.json.key.channelactivityalias.ChannelActivityAliasPolicyKeyDeserializer;
import uk.co.idv.policy.entities.policy.key.PolicyKey;
import uk.co.idv.policy.entities.policy.key.ChannelActivityAliasPolicyKey;
import uk.co.idv.policy.entities.policy.key.ChannelActivityPolicyKey;
import uk.co.idv.policy.entities.policy.key.ChannelPolicyKey;

public class PolicyKeyModule extends SimpleModule {

    public PolicyKeyModule() {
        super("policy-key-module", Version.unknownVersion());
        setUpDefaults();
        setUpChannelPolicyKey();
        setUpChannelActivityPolicyKey();
        setUpChannelActivityAliasPolicyKey();
    }

    private void setUpDefaults() {
        addDeserializer(PolicyKey.class, new PolicyKeyDeserializer());
    }

    private void setUpChannelPolicyKey() {
        setMixInAnnotation(ChannelPolicyKey.class, ChannelPolicyKeyMixin.class);
        addDeserializer(ChannelPolicyKey.class, new ChannelPolicyKeyDeserializer());
    }

    private void setUpChannelActivityPolicyKey() {
        setMixInAnnotation(ChannelActivityPolicyKey.class, ChannelActivityPolicyKeyMixin.class);
        addDeserializer(ChannelActivityPolicyKey.class, new ChannelActivityPolicyKeyDeserializer());
    }

    private void setUpChannelActivityAliasPolicyKey() {
        addDeserializer(ChannelActivityAliasPolicyKey.class, new ChannelActivityAliasPolicyKeyDeserializer());
    }

}
