package uk.co.idv.identity.usecases.protect.mask;

import uk.co.mruoc.string.mask.IgnoreLastNCharsMasker;

public class PhoneNumberMasker extends IgnoreLastNCharsMasker {

    public PhoneNumberMasker() {
        super(3);
    }

}