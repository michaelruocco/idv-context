package uk.co.idv.context.adapter.json.policy.key.channel;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface ChannelPolicyKeyJsonMother {

    static String defaultChannelKey() {
        return loadContentFromClasspath("policy-key/channel-policy-key.json");
    }

}
