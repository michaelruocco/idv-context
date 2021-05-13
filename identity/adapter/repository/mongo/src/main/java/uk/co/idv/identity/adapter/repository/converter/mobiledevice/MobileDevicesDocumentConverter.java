package uk.co.idv.identity.adapter.repository.converter.mobiledevice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.adapter.repository.document.MobileDeviceDocument;
import uk.co.idv.identity.entities.mobiledevice.MobileDevice;
import uk.co.idv.identity.entities.mobiledevice.MobileDevices;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class MobileDevicesDocumentConverter {

    public MobileDevices toMobileDevices(Collection<MobileDeviceDocument> documents) {
        Collection<MobileDevice> numbers = documents.stream()
                .map(this::toMobileDevice)
                .collect(Collectors.toList());
        return new MobileDevices(numbers);
    }

    public MobileDevice toMobileDevice(MobileDeviceDocument document) {
        return new MobileDevice(document.getToken());
    }

    public Collection<MobileDeviceDocument> toDocuments(MobileDevices mobileDevices) {
        return mobileDevices.stream().map(this::toDocument).collect(Collectors.toList());
    }

    public MobileDeviceDocument toDocument(MobileDevice device) {
        return MobileDeviceDocument.builder()
                .token(device.getToken())
                .build();
    }

}
