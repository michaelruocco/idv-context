package uk.co.idv.context.entities.alias;

public interface UnsupportedAliasTypeExceptionMother {

    static UnsupportedAliasTypeException build() {
        return new UnsupportedAliasTypeException("unsupported-type");
    }

}
