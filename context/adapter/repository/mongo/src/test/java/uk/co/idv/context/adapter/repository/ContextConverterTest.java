package uk.co.idv.context.adapter.repository;

import org.bson.Document;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;
import uk.co.mruoc.json.JsonConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ContextConverterTest {

    private final JsonConverter jsonConverter = mock(JsonConverter.class);

    private final ContextConverter converter = new ContextConverter(jsonConverter);

    @Test
    void shouldConvertDocumentToContext() {
        String json = "json";
        Document document = givenDocumentWithJson(json);
        Context expectedContext = givenJsonConvertsToContext(json);

        Context context = converter.toContext(document);

        assertThat(context).isEqualTo(expectedContext);
    }

    @Test
    void shouldConvertContextToDocument() {
        Context context = ContextMother.build();
        givenContextConvertsToJson(context);

        Document document = converter.toDocument(context);

        assertThat(document.toJson()).isEqualTo("");
    }

    private Document givenDocumentWithJson(String json) {
        Document document = mock(Document.class);
        given(document.toJson()).willReturn(json);
        return document;
    }

    private Context givenJsonConvertsToContext(String json) {
        Context context = ContextMother.build();
        given(jsonConverter.toObject(json, Context.class)).willReturn(context);
        return context;
    }

    private String givenContextConvertsToJson(Context context) {
        String json = "{}";
        given(jsonConverter.toJson(context)).willReturn(json);
        return json;
    }

}