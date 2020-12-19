package uk.co.idv.context.adapter.json.context.create;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.context.entities.activity.Activity;
import uk.co.idv.context.entities.policy.sequence.SequencePolicies;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.channel.Channel;

public interface ServiceCreateContextRequestMixin extends CreateContextRequestMixin {

    @JsonIgnore
    Channel getChannel();

    @JsonIgnore
    Aliases getAliases();

    @JsonIgnore
    Activity getActivity();

    @JsonIgnore
    IdvId getIdvId();

    @JsonIgnore
    SequencePolicies getSequencePolicies();

    @JsonIgnore
    boolean isProtectSensitiveData();

}
