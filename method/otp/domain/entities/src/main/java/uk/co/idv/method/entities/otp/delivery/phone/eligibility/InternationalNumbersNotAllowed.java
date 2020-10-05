package uk.co.idv.method.entities.otp.delivery.phone.eligibility;

import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.method.entities.eligibility.Ineligible;

public class InternationalNumbersNotAllowed extends Ineligible {

    public InternationalNumbersNotAllowed(CountryCode country) {
        super(String.format("international phone numbers not allowed (local country %s)", country));
    }

}
