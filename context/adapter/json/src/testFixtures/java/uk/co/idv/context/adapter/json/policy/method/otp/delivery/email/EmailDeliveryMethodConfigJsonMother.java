
package uk.co.idv.context.adapter.json.policy.method.otp.delivery.email;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface EmailDeliveryMethodConfigJsonMother {

    static String email() {
        return loadContentFromClasspath("method/otp/delivery/email/email-delivery-method-config.json");
    }

}
