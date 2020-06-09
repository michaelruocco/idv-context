package uk.co.idv.context.entities.alias;

import lombok.Data;

import java.util.UUID;

@Data
public class IdvId implements Alias {

    static final String TYPE = "idv-id";

    private final UUID value;

    protected IdvId(String value) {
        this(UUID.fromString(value));
    }

    protected IdvId(UUID value) {
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
