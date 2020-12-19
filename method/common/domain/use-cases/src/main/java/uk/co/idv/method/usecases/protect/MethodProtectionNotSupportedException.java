package uk.co.idv.method.usecases.protect;

public class MethodProtectionNotSupportedException extends RuntimeException {

    public MethodProtectionNotSupportedException(String name) {
        super(name);
    }

}
