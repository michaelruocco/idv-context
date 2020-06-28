package uk.co.idv.context.adapter.json.channel;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface GbRsaJsonMother {

    static String gbRsa() {
        return loadContentFromClasspath("channel/gb/gb-rsa.json");
    }

    static String gbRsaWithoutIssuerSessionId() {
        return loadContentFromClasspath("channel/gb/gb-rsa-without-issuer-session-id.json");
    }

}
