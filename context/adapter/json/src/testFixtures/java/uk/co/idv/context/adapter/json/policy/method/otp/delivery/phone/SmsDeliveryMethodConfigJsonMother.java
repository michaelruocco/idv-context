
package uk.co.idv.context.adapter.json.policy.method.otp.delivery.phone;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface SmsDeliveryMethodConfigJsonMother {

    static String sms() {
        return loadContentFromClasspath("method/otp/delivery/phone/sms-delivery-method-config.json");
    }

}
