package uk.co.idv.context.adapter.json.context.create;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface FacadeCreateContextRequestJsonMother {

    static String build() {
        return loadContentFromClasspath("context/create/facade-create-context-request.json");
    }

    static String withChannelData() {
        return loadContentFromClasspath("context/create/facade-create-context-request-with-channel-data.json");
    }

    static String withMaskedChannelEmailAddresses() {
        return loadContentFromClasspath("context/create/facade-create-context-request-with-masked-channel-email-addresses.json");
    }

    static String withMaskedChannelPhoneNumbers() {
        return loadContentFromClasspath("context/create/facade-create-context-request-with-masked-channel-phone-numbers.json");
    }

}
