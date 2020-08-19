package uk.co.idv.context.adapter.json.error.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;

import static org.mockito.Mockito.mock;

public interface JsonParseExceptionMother {

    static JsonParseException buildJsonParseException() {
        JsonParser parser = mock(JsonParser.class);
        return new JsonParseException(parser, "my-message");
    }

}
