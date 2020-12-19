package uk.co.idv.identity.entities;

public interface Updatable<T> {

    String getValue();

    T withValue(String value);

}
