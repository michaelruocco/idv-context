package uk.co.idv.context.usecases.lockout.attempt;

import lombok.Builder;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.lockout.attempt.Attempts;
import uk.co.idv.context.usecases.identity.find.FindIdentity;

@Builder
public class AttemptFacade {

    private final FindIdentity findIdentity;
    private final LoadAttempts loadAttempts;

    public Attempts load(Alias alias) {
        //TODO replace with idv id lookup
        Identity identity = findIdentity.find(alias);
        return loadAttempts.load(identity.getIdvId());
    }

}
