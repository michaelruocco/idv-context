package uk.co.idv.identity.usecases.identity.create;

import uk.co.idv.identity.entities.identity.CountryNotProvidedException;
import uk.co.idv.identity.entities.identity.Identity;

public class IdentityValidator {

    public boolean validate(Identity identity) {
        if (!identity.hasCountry()) {
           throw new CountryNotProvidedException();
        }
        return true;
    }

}
