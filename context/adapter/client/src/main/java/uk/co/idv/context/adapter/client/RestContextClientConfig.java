package uk.co.idv.context.adapter.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RestContextClientConfig implements ContextClientConfig {

    private final String baseUri;
    private final ObjectMapper mapper;

}
