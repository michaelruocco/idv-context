package uk.co.idv.context.usecases.identity.create;

public class IdentityMustBelongToCountryException extends RuntimeException {

    public IdentityMustBelongToCountryException() {
        super("identity must belong to country");
    }

}
