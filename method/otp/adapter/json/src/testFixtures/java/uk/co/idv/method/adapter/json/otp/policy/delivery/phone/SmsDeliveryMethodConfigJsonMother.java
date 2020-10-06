
package uk.co.idv.method.adapter.json.otp.policy.delivery.phone;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface SmsDeliveryMethodConfigJsonMother {

    static String sms() {
        return loadContentFromClasspath("policy/delivery/phone/sms-delivery-method-config.json");
    }

}
