package uk.co.idv.context.adapter.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;

@Builder
public class RestContextClientConfig implements ContextClientConfig {

    private final String baseUrl;
    private final ObjectMapper mapper;

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

    @Override
    public ObjectMapper getMapper() {
        return mapper;
    }

}
