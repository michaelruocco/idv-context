package uk.co.idv.context.entities.context;

public class MethodAlreadyCompleteException extends RuntimeException {

    public MethodAlreadyCompleteException(String name) {
        super(name);
    }

}
