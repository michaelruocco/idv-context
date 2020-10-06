
package uk.co.idv.method.adapter.json.otp.policy.delivery;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface InvalidDeliveryMethodConfigJsonMother {

    static String invalid() {
        return loadContentFromClasspath("policy/delivery/invalid-delivery-method-config.json");
    }

}
