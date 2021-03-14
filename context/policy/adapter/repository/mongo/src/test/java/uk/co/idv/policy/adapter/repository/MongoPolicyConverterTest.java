package uk.co.idv.policy.adapter.repository;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import uk.co.idv.policy.adapter.json.PolicyJsonMother;
import uk.co.idv.policy.entities.policy.FakePolicyMother;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.mruoc.json.JsonConverter;

import java.util.UUID;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class MongoPolicyConverterTest {

    private static final String JSON = PolicyJsonMother.build();
    private static final Class<Policy> TYPE = Policy.class;
    private static final String ID_FIELD_NAME = "_id";

    private final JsonConverter jsonConverter = mock(JsonConverter.class);

    private final MongoPolicyConverter<Policy> converter = new MongoPolicyConverter<>(ID_FIELD_NAME, TYPE, jsonConverter);

    @Test
    void shouldConvertDocumentToPolicy() {
        Document document = givenDocumentWithJson();
        Policy expectedPolicy = givenJsonConvertsToPolicy();

        Policy policy = converter.toPolicy(document);

        assertThat(policy).isEqualTo(expectedPolicy);
    }

    @Test
    void shouldConvertPolicyToDocument() {
        Policy policy = FakePolicyMother.build();
        givenConvertsToJson(policy);

        Document document = converter.toDocument(policy);

        assertThatJson(document.toJson())
                .whenIgnoringPaths("_id")
                .isEqualTo(JSON);
        assertThatJson(document.toJson())
                .inPath("_id")
                .isEqualTo(policy.getId());
    }

    @Test
    void shouldConvertIdToFindByIdQuery() {
        UUID id = UUID.fromString("c8e6866b-4e3e-4e84-b187-0dd42da116e6");

        Bson query = converter.toFindByIdQuery(id);

        assertThat(query).hasToString(
                "Filter{fieldName='_id', value=c8e6866b-4e3e-4e84-b187-0dd42da116e6}"
        );
    }

    private Document givenDocumentWithJson() {
        Document document = mock(Document.class);
        given(document.toJson()).willReturn(JSON);
        return document;
    }

    private Policy givenJsonConvertsToPolicy() {
        Policy policy = FakePolicyMother.build();
        given(jsonConverter.toObject(JSON, TYPE)).willReturn(policy);
        return policy;
    }

    private void givenConvertsToJson(Policy policy) {
        given(jsonConverter.toJson(policy)).willReturn(JSON);
    }

}
