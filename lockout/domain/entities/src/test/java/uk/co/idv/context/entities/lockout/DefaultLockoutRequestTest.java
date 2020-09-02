package uk.co.idv.context.entities.lockout;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.alias.IdvIdMother;

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
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.build();

        LockoutRequest request = DefaultLockoutRequest.builder()
                .externalRequest(externalRequest)
                .build();

        assertThat(request.getChannelId()).isEqualTo(externalRequest.getChannelId());
    }

    @Test
    void shouldReturnActivityNameFromExternalRequest() {
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.build();

        LockoutRequest request = DefaultLockoutRequest.builder()
                .externalRequest(externalRequest)
                .build();

        assertThat(request.getActivityName()).isEqualTo(externalRequest.getActivityName());
    }

    @Test
    void shouldReturnAliasesFromExternalRequest() {
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.build();

        LockoutRequest request = DefaultLockoutRequest.builder()
                .externalRequest(externalRequest)
                .build();

        assertThat(request.getAliases()).isEqualTo(externalRequest.getAliases());
    }

    @Test
    void shouldReturnAliasTypesFromExternalRequest() {
        ExternalLockoutRequest externalRequest = DefaultExternalLockoutRequestMother.build();

        LockoutRequest request = DefaultLockoutRequest.builder()
                .externalRequest(externalRequest)
                .build();

        assertThat(request.getAliasTypes()).isEqualTo(externalRequest.getAliasTypes());
    }

}
