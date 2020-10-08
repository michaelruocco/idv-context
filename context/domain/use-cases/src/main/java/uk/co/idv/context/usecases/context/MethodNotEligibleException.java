package uk.co.idv.context.usecases.context;

public class MethodNotEligibleException extends RuntimeException {

    public MethodNotEligibleException(String name) {
        super(name);
    }

}
