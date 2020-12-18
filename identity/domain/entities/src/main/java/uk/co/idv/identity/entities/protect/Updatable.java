package uk.co.idv.identity.entities.protect;

public interface Updatable<T> {

    String getValue();

    T withValue(String value);

}
