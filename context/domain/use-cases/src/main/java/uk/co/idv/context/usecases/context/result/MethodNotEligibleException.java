package uk.co.idv.context.usecases.context.result;

public class MethodNotEligibleException extends RuntimeException {

    public MethodNotEligibleException(String name) {
        super(name);
    }

}
