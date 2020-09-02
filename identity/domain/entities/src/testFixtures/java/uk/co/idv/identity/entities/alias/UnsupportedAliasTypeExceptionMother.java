package uk.co.idv.identity.entities.alias;

public interface UnsupportedAliasTypeExceptionMother {

    static UnsupportedAliasTypeException build() {
        return new UnsupportedAliasTypeException("unsupported-type");
    }

}
