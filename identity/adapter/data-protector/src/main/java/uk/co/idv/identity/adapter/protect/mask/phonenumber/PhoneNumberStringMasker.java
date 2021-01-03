package uk.co.idv.identity.adapter.protect.mask.phonenumber;

import uk.co.mruoc.string.mask.IgnoreLastNCharsMasker;

public class PhoneNumberStringMasker extends IgnoreLastNCharsMasker {

    public PhoneNumberStringMasker() {
        super(3);
    }

}
