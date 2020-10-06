package uk.co.idv.method.adapter.json.otp.delivery;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface EmailDeliveryMethodJsonMother {

    static String email() {
        return loadContentFromClasspath("delivery/email-delivery-method.json");
    }

}
