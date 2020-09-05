package uk.co.idv.context.usecases.context.method;

public class MethodBuilderNotConfiguredException extends RuntimeException {

    public MethodBuilderNotConfiguredException(String name) {
        super(name);
    }

}
