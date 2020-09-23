package uk.co.idv.context.adapter.json.context.method.otp.delivery;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface VoiceDeliveryMethodJsonMother {

    static String voice() {
        return loadContentFromClasspath("context/method/otp/delivery/voice-delivery-method.json");
    }

}
