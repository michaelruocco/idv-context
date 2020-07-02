package uk.co.idv.context.usecases.identity.create;

import uk.co.idv.context.entities.identity.Identity;

public class IdentityValidator {

    public boolean validate(Identity identity) {
        if (!identity.hasCountry()) {
           throw new CountryNotProvidedException();
        }
        return true;
    }

}
