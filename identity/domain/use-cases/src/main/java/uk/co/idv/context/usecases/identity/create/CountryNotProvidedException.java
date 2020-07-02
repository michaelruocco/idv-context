package uk.co.idv.context.usecases.identity.create;

public class CountryNotProvidedException extends RuntimeException {

    public CountryNotProvidedException() {
        super("cannot create an identity without a country");
    }

}
