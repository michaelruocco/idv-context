package uk.co.idv.context.adapter.json.channel;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface DeRsaJsonMother {

    static String deRsa() {
        return loadContentFromClasspath("channel/de/de-rsa.json");
    }

}
