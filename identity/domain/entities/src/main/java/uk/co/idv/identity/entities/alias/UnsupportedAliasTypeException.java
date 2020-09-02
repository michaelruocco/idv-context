package uk.co.idv.identity.entities.alias;

public class UnsupportedAliasTypeException extends RuntimeException {

    public UnsupportedAliasTypeException(String type) {
        super(type);
    }

}
