package uk.co.idv.method.adapter.json.otp.delivery;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface SmsDeliveryMethodJsonMother {

    static String sms() {
        return loadContentFromClasspath("delivery/sms-delivery-method.json");
    }

    static String smsIneligible() {
        return loadContentFromClasspath("delivery/sms-delivery-method-ineligible.json");
    }

    static String smsWithLastUpdated() {
        return loadContentFromClasspath("delivery/sms-delivery-method-with-last-updated.json");
    }

}
