package uk.co.idv.identity.adapter.json.channel;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface DeRsaJsonMother {

    static String deRsa() {
        return loadContentFromClasspath("channel/de/de-rsa.json");
    }

}
