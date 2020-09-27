package uk.co.idv.identity.adapter.json.channel;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface AbcJsonMother {

    static String abc() {
        return loadContentFromClasspath("channel/gb/abc.json");
    }

}
