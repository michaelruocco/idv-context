package uk.co.idv.context.usecases.identity.data;

import uk.co.idv.context.entities.phonenumber.PhoneNumbers;
import uk.co.idv.context.usecases.identity.LoadIdentityRequest;

public interface PhoneNumberLoader {

    PhoneNumbers load(LoadIdentityRequest request);

}
