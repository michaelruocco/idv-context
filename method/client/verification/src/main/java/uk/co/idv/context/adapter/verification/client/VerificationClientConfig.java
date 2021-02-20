package uk.co.idv.context.adapter.verification.client;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface VerificationClientConfig {

    String getBaseUri();

    ObjectMapper getMapper();

}
