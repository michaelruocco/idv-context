package uk.co.idv.context.entities.alias;

public class AliasOfTypeNotFoundException extends RuntimeException {

    public AliasOfTypeNotFoundException(String type) {
        super(type);
    }

}
