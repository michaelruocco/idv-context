
package uk.co.idv.method.adapter.json.otp.policy.delivery.email;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface EmailDeliveryMethodConfigJsonMother {

    static String email() {
        return loadContentFromClasspath("policy/delivery/email/email-delivery-method-config.json");
    }

}
