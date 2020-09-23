package uk.co.idv.context.adapter.json.context.method.otp.delivery;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface DeliveryMethodsJsonMother {

    static String oneOfEach() {
        return loadContentFromClasspath("context/method/otp/delivery/delivery-methods.json");
    }

}
