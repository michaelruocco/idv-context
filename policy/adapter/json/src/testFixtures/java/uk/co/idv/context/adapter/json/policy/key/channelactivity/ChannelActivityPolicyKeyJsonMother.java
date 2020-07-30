package uk.co.idv.context.adapter.json.policy.key.channelactivity;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface ChannelActivityPolicyKeyJsonMother {

    static String defaultChannelActivityKey() {
        return loadContentFromClasspath("policy-key/channel-activity-policy-key.json");
    }

}
