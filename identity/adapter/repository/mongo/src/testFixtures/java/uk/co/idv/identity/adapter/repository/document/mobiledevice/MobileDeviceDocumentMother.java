package uk.co.idv.identity.adapter.repository.document.mobiledevice;

import uk.co.idv.identity.adapter.repository.document.MobileDeviceDocument;

import java.util.Arrays;
import java.util.Collection;

public class MobileDeviceDocumentMother {

    private MobileDeviceDocumentMother() {
        // utility class
    }

    public static Collection<MobileDeviceDocument> two() {
        return Arrays.asList(device1(), device2());
    }

    public static MobileDeviceDocument device1() {
        return builder().build();
    }

    public static MobileDeviceDocument device2() {
        return builder().token("2dd1121057f63af3c57ccf").build();
    }

    private static MobileDeviceDocument.MobileDeviceDocumentBuilder builder() {
        return MobileDeviceDocument.builder()
                .token("8cc1121057f63af3c57bbe");
    }

}
