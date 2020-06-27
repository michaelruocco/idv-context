package uk.co.idv.context.entities.alias;

public class EmptyAliasesException extends RuntimeException {

    public EmptyAliasesException() {
        super("cannot get first value of empty aliases");
    }

}
