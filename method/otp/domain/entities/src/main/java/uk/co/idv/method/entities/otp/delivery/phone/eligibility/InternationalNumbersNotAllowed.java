package uk.co.idv.method.entities.otp.delivery.eligibility.phone;

import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.method.entities.eligibility.Ineligible;

public class InternationalNumbersNotAllowed extends Ineligible {

    public InternationalNumbersNotAllowed(CountryCode country) {
        super(String.format("international phone numbers not allowed (local country %s)", country));
    }

}
