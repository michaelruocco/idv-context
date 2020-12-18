package uk.co.idv.identity.entities.protect;

public class MaskingNotSupportedException extends RuntimeException {

    public MaskingNotSupportedException(Class<?> type) {
        super(type.getName());
    }

}
