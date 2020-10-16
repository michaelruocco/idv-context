package uk.co.idv.policy.adapter.json.key.channelactivity;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface ChannelActivityPolicyKeyJsonMother {

    static String build() {
        return loadContentFromClasspath("policy-key/channel-activity-policy-key.json");
    }

}
