package uk.co.idv.context.adapter.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class JsonNodeConverterTest {

    @Test
    void shouldThrowUncheckedIOExceptionIfJacksonThrowsIOException() throws IOException {
        JsonParser parser = mock(JsonParser.class);
        ObjectCodec codec = mock(ObjectCodec.class);
        given(parser.getCodec()).willReturn(codec);
        JsonNode node = mock(JsonNode.class);
        given(node.traverse(codec)).willReturn(parser);
        Class<Object> type = Object.class;
        given(parser.readValueAs(type)).willThrow(IOException.class);

        Throwable error = catchThrowable(() -> JsonNodeConverter.toObject(node, parser, type));

        assertThat(error)
                .isInstanceOf(UncheckedIOException.class)
                .hasCauseInstanceOf(IOException.class);
    }

}
