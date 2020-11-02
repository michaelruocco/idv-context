package uk.co.idv.app.spring.filters.logging.masking.emailaddress;

import uk.co.mruoc.json.mask.MaskFunction;
import uk.co.mruoc.json.mask.string.IgnoreFirstNAndLastNCharsMasker;

public class EmailAddressMaskFunction extends MaskFunction {

    public EmailAddressMaskFunction() {
        super(new IgnoreFirstNAndLastNCharsMasker(3, 6));
    }

}
