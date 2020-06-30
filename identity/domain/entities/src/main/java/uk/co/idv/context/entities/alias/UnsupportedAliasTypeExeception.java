package uk.co.idv.context.entities.alias;

public class UnsupportedAliasTypeExeception extends RuntimeException {

    public UnsupportedAliasTypeExeception(String type) {
        super(type);
    }

}
