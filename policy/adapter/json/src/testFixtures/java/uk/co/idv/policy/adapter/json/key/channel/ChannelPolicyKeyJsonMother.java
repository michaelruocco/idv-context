package uk.co.idv.policy.adapter.json.key.channel;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface ChannelPolicyKeyJsonMother {

    static String build() {
        return loadContentFromClasspath("policy-key/channel-policy-key.json");
    }

}
