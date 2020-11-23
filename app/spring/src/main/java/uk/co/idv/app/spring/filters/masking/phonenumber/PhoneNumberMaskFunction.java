package uk.co.idv.app.spring.filters.masking.phonenumber;

import uk.co.mruoc.json.mask.MaskFunction;
import uk.co.mruoc.json.mask.string.IgnoreLastNCharsMasker;

public class PhoneNumberMaskFunction extends MaskFunction {

    public PhoneNumberMaskFunction() {
        super(new IgnoreLastNCharsMasker(3));
    }

}
