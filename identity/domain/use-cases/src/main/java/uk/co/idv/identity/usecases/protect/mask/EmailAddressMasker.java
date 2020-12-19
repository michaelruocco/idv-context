package uk.co.idv.identity.usecases.protect.mask;

import uk.co.mruoc.string.mask.IgnoreFirstNAndLastNCharsMasker;

public class EmailAddressMasker extends IgnoreFirstNAndLastNCharsMasker {

    public EmailAddressMasker() {
        super(4, 6);
    }

}
