package uk.co.idv.context.usecases.identity.data;

import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.usecases.identity.LoadIdentityRequest;

public interface EmailAddressLoader {

    EmailAddresses load(LoadIdentityRequest request);

}
