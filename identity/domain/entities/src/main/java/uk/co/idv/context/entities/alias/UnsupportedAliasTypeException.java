package uk.co.idv.context.entities.alias;

public class UnsupportedAliasTypeException extends RuntimeException {

    public UnsupportedAliasTypeException(String type) {
        super(type);
    }

}
