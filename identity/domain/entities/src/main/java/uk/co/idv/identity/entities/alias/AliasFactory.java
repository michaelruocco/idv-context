package uk.co.idv.identity.entities.alias;

public interface AliasFactory {

    Alias build(String type, String value);

}
