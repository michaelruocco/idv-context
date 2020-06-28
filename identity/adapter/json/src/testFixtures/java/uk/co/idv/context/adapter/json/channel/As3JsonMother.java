package uk.co.idv.context.adapter.json.channel;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface As3JsonMother {

    static String as3() {
        return loadContentFromClasspath("channel/gb/as3.json");
    }

}
