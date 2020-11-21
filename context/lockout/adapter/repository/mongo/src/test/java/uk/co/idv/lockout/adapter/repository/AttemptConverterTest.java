package uk.co.idv.lockout.adapter.repository;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.alias.IdvIdMother;
import uk.co.idv.lockout.adapter.json.attempt.AttemptsJsonMother;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.attempt.AttemptsMother;
import uk.co.mruoc.json.JsonConverter;

import java.util.Arrays;
import java.util.Collection;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AttemptConverterTest {

    private static final String JSON = AttemptsJsonMother.build();

    private final JsonConverter jsonConverter = mock(JsonConverter.class);

    private final AttemptConverter converter = new AttemptConverter(jsonConverter);

    @Test
    void shouldConvertDocumentToAttempts() {
        Document document = givenDocumentWithJson();
        Attempts expectedAttempts = givenJsonConvertsToAttempts();

        Attempts attempts = converter.toAttempts(document);

        assertThat(attempts).isEqualTo(expectedAttempts);
    }

    @Test
    void shouldConvertAttemptsToDocument() {
        Attempts attempts = AttemptsMother.build();
        givenConvertsToJson(attempts);

        Document document = converter.toDocument(attempts);

        assertThatJson(document.toJson())
                .whenIgnoringPaths("_id")
                .isEqualTo(JSON);
        assertThatJson(document.toJson())
                .inPath("_id")
                .isEqualTo(attempts.getIdvId().getValue());
    }

    @Test
    void shouldConvertIdvIdsToFindByIdvIdsQuery() {
        IdvId idvId = IdvIdMother.idvId();
        IdvId otherIdvId = IdvIdMother.idvId1();
        Collection<IdvId> idvIds = Arrays.asList(idvId, otherIdvId);

        Bson query = converter.toFindByIdvIdsQuery(idvIds);

        assertThat(query).hasToString("Or Filter{filters=[" +
                "Filter{fieldName='_id', value=90b585c6-170f-42a6-ac7c-83d294bdab3f}, " +
                "Filter{fieldName='_id', value=83428996-d641-45e6-a32b-ab7c2f17ac20}" +
                "]}");
    }

    @Test
    void shouldConvertIdvIdToFindByIdvIdQuery() {
        IdvId idvId = IdvIdMother.idvId();

        Bson query = converter.toFindByIdvIdQuery(idvId);

        assertThat(query).hasToString(
                "Filter{fieldName='_id', value=90b585c6-170f-42a6-ac7c-83d294bdab3f}"
        );
    }

    private Document givenDocumentWithJson() {
        Document document = mock(Document.class);
        given(document.toJson()).willReturn(JSON);
        return document;
    }

    private Attempts givenJsonConvertsToAttempts() {
        Attempts attempts = AttemptsMother.build();
        given(jsonConverter.toObject(JSON, Attempts.class)).willReturn(attempts);
        return attempts;
    }

    private void givenConvertsToJson(Attempts attempts) {
        given(jsonConverter.toJson(attempts)).willReturn(JSON);
    }

}
