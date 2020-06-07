package uk.co.idv.context.entities.alias;

import java.util.UUID;

public class IdvIdMother {

    public static IdvId build() {
        return withValue(UUID.fromString("90b585c6-170f-42a6-ac7c-83d294bdab3f"));
    }

    public static IdvId withValue(UUID value) {
        return new IdvId(value);
    }

}
