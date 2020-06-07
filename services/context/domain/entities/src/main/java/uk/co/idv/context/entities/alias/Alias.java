package uk.co.idv.context.entities.alias;

public interface Alias {

    String getType();

    String getValue();

    boolean isCardNumber();

    default boolean isType(String type) {
        return getType().equals(type);
    }

}
