package uk.co.idv.context.usecases.lockout.attempt;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.DefaultAliasMother;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;
import uk.co.idv.context.entities.lockout.attempt.Attempts;
import uk.co.idv.context.usecases.identity.find.FindIdentity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AttemptFacadeTest {

    private final FindIdentity findIdentity = mock(FindIdentity.class);
    private final LoadAttempts loadAttempts = mock(LoadAttempts.class);

    private final AttemptFacade facade = AttemptFacade.builder()
            .findIdentity(findIdentity)
            .loadAttempts(loadAttempts)
            .build();

    @Test
    void shouldLoadAttempts() {
        Alias alias = DefaultAliasMother.build();
        Identity identity = IdentityMother.example();
        given(findIdentity.find(alias)).willReturn(identity);
        Attempts expectedAttempts = mock(Attempts.class);
        given(loadAttempts.load(identity.getIdvId())).willReturn(expectedAttempts);

        Attempts attempts = facade.load(alias);

        assertThat(attempts).isEqualTo(expectedAttempts);
    }
}
