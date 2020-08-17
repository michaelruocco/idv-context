package uk.co.idv.context.entities.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.alias.IdvIdMother;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultLockoutRequestTest {

    @Test
    void shouldReturnIdvId() {
        IdvId idvId = IdvIdMother.idvId();

        LockoutRequest request = DefaultLockoutRequest.builder()
                .idvId(idvId)
                .build();

        assertThat(request.getIdvId()).isEqualTo(idvId);
    }

    @Test
    void shouldReturnTimestamp() {
        Instant timestamp = Instant.now();

        LockoutRequest request = DefaultLockoutRequest.builder()
                .timestamp(timestamp)
                .build();

        assertThat(request.getTimestamp()).isEqualTo(timestamp);
    }

    @Test
    void shouldReturnChannelIdFromExternalRequest() {
        ExternalLockoutRequest externalRequest = ExternalLockoutRequestMother.build();

        LockoutRequest request = DefaultLockoutRequest.builder()
                .externalRequest(externalRequest)
                .build();

        assertThat(request.getChannelId()).isEqualTo(externalRequest.getChannelId());
    }

    @Test
    void shouldReturnActivityNameFromExternalRequest() {
        ExternalLockoutRequest externalRequest = ExternalLockoutRequestMother.build();

        LockoutRequest request = DefaultLockoutRequest.builder()
                .externalRequest(externalRequest)
                .build();

        assertThat(request.getActivityName()).isEqualTo(externalRequest.getActivityName());
    }

    @Test
    void shouldReturnAliasFromExternalRequest() {
        ExternalLockoutRequest externalRequest = ExternalLockoutRequestMother.build();

        LockoutRequest request = DefaultLockoutRequest.builder()
                .externalRequest(externalRequest)
                .build();

        assertThat(request.getAlias()).isEqualTo(externalRequest.getAlias());
    }

    @Test
    void shouldReturnAliasTypeFromExternalRequest() {
        ExternalLockoutRequest externalRequest = ExternalLockoutRequestMother.build();

        LockoutRequest request = DefaultLockoutRequest.builder()
                .externalRequest(externalRequest)
                .build();

        assertThat(request.getAliasType()).isEqualTo(externalRequest.getAliasType());
    }

}
