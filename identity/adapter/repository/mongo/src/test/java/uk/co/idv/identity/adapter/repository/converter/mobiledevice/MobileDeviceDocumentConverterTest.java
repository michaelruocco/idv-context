package uk.co.idv.identity.adapter.repository.converter.mobiledevice;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.adapter.repository.document.MobileDeviceDocument;
import uk.co.idv.identity.adapter.repository.document.mobiledevice.MobileDeviceDocumentMother;
import uk.co.idv.identity.entities.mobiledevice.MobileDevice;
import uk.co.idv.identity.entities.mobiledevice.MobileDeviceMother;
import uk.co.idv.identity.entities.mobiledevice.MobileDevices;
import uk.co.idv.identity.entities.mobiledevice.MobileDevicesMother;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class MobileDeviceDocumentConverterTest {

    private final MobileDeviceDocumentConverter converter = new MobileDeviceDocumentConverter();

    @Test
    void shouldConvertMultipleMobileDevicesToDocuments() {
        MobileDevices devices = MobileDevicesMother.two();

        Collection<MobileDeviceDocument> documents = converter.toDocuments(devices);

        assertThat(documents).containsExactlyElementsOf(MobileDeviceDocumentMother.two());
    }

    @Test
    void shouldConvertMobileDeviceToDocument() {
        MobileDevice device = MobileDeviceMother.device1();

        MobileDeviceDocument document = converter.toDocument(device);

        assertThat(document).isEqualTo(MobileDeviceDocumentMother.device1());
    }

    @Test
    void shouldConvertMultipleDocumentsToMobileDevices() {
        Collection<MobileDeviceDocument> documents = MobileDeviceDocumentMother.two();

        MobileDevices devices = converter.toMobileDevices(documents);

        assertThat(devices).containsExactlyElementsOf(MobileDevicesMother.two());
    }

    @Test
    void shouldConvertDocumentToMobileDevice() {
        MobileDeviceDocument document = MobileDeviceDocumentMother.device1();

        MobileDevice device = converter.toMobileDevice(document);

        assertThat(device).isEqualTo(MobileDeviceMother.device1());
    }

    @Test
    void shouldConvertNullDocumentsListToEmptyMobileDevices() {
        Collection<MobileDeviceDocument> documents = null;

        MobileDevices mobileDevices = converter.toMobileDevices(documents);

        assertThat(mobileDevices).isEmpty();
    }

}
