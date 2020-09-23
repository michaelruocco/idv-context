package uk.co.idv.context.adapter.json.context.method.otp.delivery;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface SmsDeliveryMethodJsonMother {

    static String sms() {
        return loadContentFromClasspath("context/method/otp/delivery/sms-delivery-method.json");
    }

}
