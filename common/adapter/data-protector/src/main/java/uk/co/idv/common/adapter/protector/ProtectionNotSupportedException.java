package uk.co.idv.common.adapter.protector;

public class ProtectionNotSupportedException extends RuntimeException {

    public ProtectionNotSupportedException(Class<?> type) {
        this(type.getName());
    }

    public ProtectionNotSupportedException(String message) {
        super(message);
    }

}
