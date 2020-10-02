package uk.co.idv.context.entities.context;

public class MethodNotEligibleException extends RuntimeException {

    public MethodNotEligibleException(String name) {
        super(name);
    }

}
