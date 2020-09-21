
package uk.co.idv.context.adapter.json.policy.method.otp.delivery;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface InvalidDeliveryMethodConfigJsonMother {

    static String invalid() {
        return loadContentFromClasspath("policy/method/otp/delivery/invalid-delivery-method-config.json");
    }

}
