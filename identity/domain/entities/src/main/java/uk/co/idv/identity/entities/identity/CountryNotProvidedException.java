package uk.co.idv.identity.entities.identity;

public class CountryNotProvidedException extends RuntimeException {

    public CountryNotProvidedException() {
        super("cannot create an identity without a country");
    }

}
