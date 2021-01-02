package uk.co.idv.identity.adapter.protect.mask.emailaddress;

import uk.co.mruoc.string.mask.IgnoreFirstNAndLastNCharsMasker;

public class EmailAddressStringMasker extends IgnoreFirstNAndLastNCharsMasker {

    public EmailAddressStringMasker() {
        super(4, 6);
    }

}
