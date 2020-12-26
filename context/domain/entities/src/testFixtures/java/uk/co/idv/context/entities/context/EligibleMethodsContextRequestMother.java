package uk.co.idv.context.entities.context;

import java.util.UUID;

public interface EligibleMethodsContextRequestMother {

    static EligibleMethodsContextRequest build() {
        return builder().build();
    }

    static EligibleMethodsContextRequest.EligibleMethodsContextRequestBuilder builder() {
        return EligibleMethodsContextRequest.builder()
                .contextId(UUID.fromString("2948aadc-7f63-4b00-875b-77a4e6608e5c"))
                .methodName("fake-method");
    }

}
