package uk.co.idv.identity.entities.emailaddress;

import uk.co.idv.identity.entities.identity.RequestedData;
import uk.co.idv.identity.entities.identity.RequestedDataItems;

public class EmailAddressesOnly extends RequestedData {

    public EmailAddressesOnly() {
        super(RequestedDataItems.EMAIL_ADDRESSES);
    }

}
