package uk.co.idv.context.usecases.identity.save.external;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.save.SaveIdentityStrategy;

@Slf4j
public class ExternalSaveIdentityStrategy implements SaveIdentityStrategy {

    @Override
    public Identity prepare(Identity updated, Identity existing) {
        return updated.addData(existing);
    }

}
