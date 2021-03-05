package uk.co.idv.policy.adapter.json.key;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.policy.adapter.json.key.channel.ChannelPolicyKeyDeserializer;
import uk.co.idv.policy.adapter.json.key.channel.ChannelPolicyKeyMixin;
import uk.co.idv.policy.adapter.json.key.channelactivity.ChannelActivityPolicyKeyDeserializer;
import uk.co.idv.policy.adapter.json.key.channelactivity.ChannelActivityPolicyKeyMixin;
import uk.co.idv.policy.adapter.json.key.channelactivityalias.ChannelActivityAliasPolicyKeyDeserializer;
import uk.co.idv.policy.adapter.json.key.validcookie.ValidCookiePolicyKeyDeserializer;
import uk.co.idv.policy.adapter.json.key.validcookie.ValidCookiePolicyKeyMixin;
import uk.co.idv.policy.entities.policy.key.PolicyKey;
import uk.co.idv.policy.entities.policy.key.channelactivityalias.ChannelActivityAliasPolicyKey;
import uk.co.idv.policy.entities.policy.key.channelactivity.ChannelActivityPolicyKey;
import uk.co.idv.policy.entities.policy.key.channel.ChannelPolicyKey;
import uk.co.idv.policy.entities.policy.key.validcookie.ValidCookiePolicyKey;

public class PolicyKeyModule extends SimpleModule {

    public PolicyKeyModule() {
        super("policy-key-module", Version.unknownVersion());
        setUpDefaults();
        setUpChannelPolicyKey();
        setUpChannelActivityPolicyKey();
        setUpChannelActivityAliasPolicyKey();
        setUpValidCookiePolicyKey();
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

    private void setUpValidCookiePolicyKey() {
        setMixInAnnotation(ValidCookiePolicyKey.class, ValidCookiePolicyKeyMixin.class);
        addDeserializer(ValidCookiePolicyKey.class, new ValidCookiePolicyKeyDeserializer());
    }

}
