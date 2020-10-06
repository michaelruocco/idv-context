package uk.co.idv.method.adapter.json.otp.delivery;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface DeliveryMethodsJsonMother {

    static String oneOfEach() {
        return loadContentFromClasspath("delivery/delivery-methods.json");
    }

}
