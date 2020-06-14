package uk.co.idv.context.entities.alias;

import java.util.UUID;

public interface IdvIdMother {

    static IdvId idvId() {
        return withValue("90b585c6-170f-42a6-ac7c-83d294bdab3f");
    }

    static IdvId withValue(String value) {
        return withValue(UUID.fromString(value));
    }

    static IdvId withValue(UUID value) {
        return new IdvId(value);
    }

}
