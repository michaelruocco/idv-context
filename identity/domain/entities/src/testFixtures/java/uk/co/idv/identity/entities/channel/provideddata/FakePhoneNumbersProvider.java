package uk.co.idv.identity.entities.channel.provideddata;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

@RequiredArgsConstructor
public class FakePhoneNumbersProvider implements PhoneNumbersProvider {

    private final PhoneNumbers phoneNumbers;

    @Override
    public PhoneNumbers getPhoneNumbers() {
        return phoneNumbers;
    }

}
