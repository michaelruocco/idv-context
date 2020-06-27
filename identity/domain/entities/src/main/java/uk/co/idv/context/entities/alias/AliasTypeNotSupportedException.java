package uk.co.idv.context.entities.alias;

public class AliasTypeNotSupportedException extends RuntimeException {

    public AliasTypeNotSupportedException(String type) {
        super(type);
    }

}
