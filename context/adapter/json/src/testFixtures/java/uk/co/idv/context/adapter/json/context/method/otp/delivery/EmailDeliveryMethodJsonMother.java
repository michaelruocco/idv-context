package uk.co.idv.context.adapter.json.context.method.otp.delivery;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface EmailDeliveryMethodJsonMother {

    static String email() {
        return loadContentFromClasspath("context/method/otp/delivery/email-delivery-method.json");
    }

}
