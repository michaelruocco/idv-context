package uk.co.idv.context.entities.identity;

import com.neovisionaries.i18n.CountryCode;

public interface CountryMismatchExceptionMother {

    static CountryMismatchException build() {
        return new CountryMismatchException(
                CountryCode.GB,
                CountryCode.DE
        );
    }

}
