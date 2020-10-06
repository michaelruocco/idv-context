package uk.co.idv.method.adapter.json.otp.delivery;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface VoiceDeliveryMethodJsonMother {

    static String voice() {
        return loadContentFromClasspath("delivery/voice-delivery-method.json");
    }

}
