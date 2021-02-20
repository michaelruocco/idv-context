package uk.co.idv.context.adapter.verification.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RestVerificationClientConfig implements VerificationClientConfig {

    private final String baseUri;
    private final ObjectMapper mapper;

}
