package uk.co.idv.context.usecases.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.context.entities.lockout.ExternalLockoutRequest;
import uk.co.idv.context.entities.lockout.LockoutRequest;

import java.time.Clock;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ExternalLockoutRequestConverterTest {

    private final Clock clock = mock(Clock.class);

    private final ExternalLockoutRequestConverter converter = new ExternalLockoutRequestConverter(clock);

    @Test
    void shouldPopulateCurrentTimeAsTimestampOnRequest() {
        Instant now = givenCurrentTime();

        LockoutRequest request = converter.toLockoutRequest(mock(ExternalLockoutRequest.class), mock(IdvId.class));

        assertThat(request.getTimestamp()).isEqualTo(now);
    }

    @Test
    void shouldPopulateExternalRequestOnRequest() {
        ExternalLockoutRequest externalRequest = mock(ExternalLockoutRequest.class);

        LockoutRequest request = converter.toLockoutRequest(externalRequest, mock(IdvId.class));

        assertThat(request.getChannelId()).isEqualTo(externalRequest.getChannelId());
        assertThat(request.getActivityName()).isEqualTo(externalRequest.getActivityName());
        assertThat(request.getAliases()).isEqualTo(externalRequest.getAliases());
    }

    @Test
    void shouldPopulateIdvIdOnRequest() {
        IdvId idvId = mock(IdvId.class);

        LockoutRequest request = converter.toLockoutRequest(mock(ExternalLockoutRequest.class), idvId);

        assertThat(request.getIdvId()).isEqualTo(idvId);
    }

    private Instant givenCurrentTime() {
        Instant now = Instant.now();
        given(clock.instant()).willReturn(now);
        return now;
    }
}
