package uk.co.idv.identity.entities.alias;

import lombok.Data;

import java.util.UUID;

@Data
public class IdvId implements Alias {

    public static final String TYPE = "idv-id";

    private final UUID value;

    public IdvId(String value) {
        this(UUID.fromString(value));
    }

    public IdvId(UUID value) {
        this.value = value;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public String getValue() {
        return value.toString();
    }

    @Override
    public boolean isCardNumber() {
        return false;
    }

    public UUID getValueAsUuid() {
        return value;
    }

    public static boolean isIdvId(Alias alias) {
        return alias.isType(TYPE);
    }

}
