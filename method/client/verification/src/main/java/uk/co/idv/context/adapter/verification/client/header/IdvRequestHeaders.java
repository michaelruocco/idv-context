package uk.co.idv.context.adapter.verification.client.header;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static uk.co.idv.context.adapter.verification.client.header.IdvHeaderConstants.AUTHORIZATION_NAME;
import static uk.co.idv.context.adapter.verification.client.header.IdvHeaderConstants.CHANNEL_ID_NAME;
import static uk.co.idv.context.adapter.verification.client.header.IdvHeaderConstants.CORRELATION_ID_NAME;

@Builder
@Data
public class IdvRequestHeaders {

    private final String channelId;
    private final String authorization;

    @Builder.Default
    private final UUID correlationId = UUID.randomUUID();

    public static IdvRequestHeaders build(Map<String, String> values) {
        IdvRequestHeaders.IdvRequestHeadersBuilder builder = IdvRequestHeaders.builder()
                .channelId(values.get(CHANNEL_ID_NAME))
                .correlationId(extractCorrelationId(values));
        Optional.ofNullable(values.get(AUTHORIZATION_NAME)).ifPresent(builder::authorization);
        return builder.build();
    }

    public Optional<String> getAuthorization() {
        return Optional.ofNullable(authorization);
    }

    public String[] toArray() {
        Collection<String> values = new ArrayList<>();
        addChannelId(values);
        addCorrelationId(values);
        addAuthorizationIfPresent(values);
        return values.toArray(new String[0]);
    }

    private void addChannelId(Collection<String> values) {
        values.add(CHANNEL_ID_NAME);
        values.add(channelId);
    }

    private void addCorrelationId(Collection<String> values) {
        values.add(CORRELATION_ID_NAME);
        values.add(correlationId.toString());
    }

    private void addAuthorizationIfPresent(Collection<String> values) {
        if (StringUtils.isEmpty(authorization)) {
            return;
        }
        values.add(AUTHORIZATION_NAME);
        values.add(authorization);
    }

    private static UUID extractCorrelationId(Map<String, String> values) {
        return Optional.ofNullable(values.get(CORRELATION_ID_NAME))
                .map(UUID::fromString)
                .orElse(UUID.randomUUID());
    }

}
