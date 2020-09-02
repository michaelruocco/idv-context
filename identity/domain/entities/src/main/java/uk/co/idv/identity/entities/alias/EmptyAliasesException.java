package uk.co.idv.identity.entities.alias;

public class EmptyAliasesException extends RuntimeException {

    public EmptyAliasesException() {
        super("cannot get first value of empty aliases");
    }

}
