package uk.co.idv.identity.adapter.json.alias.idvid;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.identity.adapter.json.alias.AliasMixin;

import java.util.UUID;

public interface IdvIdMixin extends AliasMixin {

    @JsonIgnore
    UUID getValueAsUuid();

}
