package uk.co.idv.context.entities.phonenumber;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public abstract class AbstractPhoneNumber implements PhoneNumber {

    private final String type;
    private final String value;

}
