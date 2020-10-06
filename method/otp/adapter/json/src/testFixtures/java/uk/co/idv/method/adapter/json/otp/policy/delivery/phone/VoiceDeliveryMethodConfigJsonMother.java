
package uk.co.idv.method.adapter.json.otp.policy.delivery.phone;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface VoiceDeliveryMethodConfigJsonMother {

    static String voice() {
        return loadContentFromClasspath("policy/delivery/phone/voice-delivery-method-config.json");
    }

}
