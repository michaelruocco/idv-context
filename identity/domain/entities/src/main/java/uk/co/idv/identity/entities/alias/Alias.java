package uk.co.idv.identity.entities.alias;

public interface Alias {

    String getType();

    String getValue();

    boolean isCardNumber();

    default boolean isIdvId() {
        return isType(IdvId.TYPE);
    }

    default boolean isType(String type) {
        return getType().equals(type);
    }

    default boolean valueEndsWith(String suffix) {
        return getValue().endsWith(suffix);
    }

    default String format() {
        return String.format("%s|%s", getType(), getValue());
    }

}
