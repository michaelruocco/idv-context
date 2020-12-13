package uk.co.idv.context.adapter.client;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface ContextClientConfig {

    String getBaseUri();

    ObjectMapper getMapper();

}
