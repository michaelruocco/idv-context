package uk.co.idv.context.adapter.json.policy.key.channelactivityalias;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface ChannelActivityAliasPolicyKeyJsonMother {

    static String build() {
        return loadContentFromClasspath("policy-key/channel-activity-alias-policy-key.json");
    }

}
