package uk.co.idv.policy.adapter.json.key.channelactivityalias;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface ChannelActivityAliasPolicyKeyJsonMother {

    static String build() {
        return loadContentFromClasspath("policy-key/channel-activity-alias-policy-key.json");
    }

}
