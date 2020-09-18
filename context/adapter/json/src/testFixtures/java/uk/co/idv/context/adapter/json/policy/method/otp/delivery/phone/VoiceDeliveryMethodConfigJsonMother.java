
package uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface VoiceDeliveryMethodConfigJsonMother {

    static String voice() {
        return loadContentFromClasspath("method/otp/delivery/phone/voice-delivery-method-config.json");
    }

}
