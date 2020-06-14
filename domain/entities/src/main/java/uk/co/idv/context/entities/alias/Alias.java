package uk.co.idv.context.entities.alias;

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

    default String format() {
        return String.format("%s|%s", getType(), getValue());
    }

}
