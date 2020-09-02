package uk.co.idv.identity.adapter.json.channel;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface DefaultChannelJsonMother {

    static String build() {
        return loadContentFromClasspath("channel/default-channel.json");
    }

}
