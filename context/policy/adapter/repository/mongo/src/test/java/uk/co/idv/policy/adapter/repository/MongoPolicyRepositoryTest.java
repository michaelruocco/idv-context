package uk.co.idv.policy.adapter.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.policy.entities.policy.FakePolicyMother;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.Policy;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MongoPolicyRepositoryTest {

    private final MongoCollection<Document> collection = mock(MongoCollection.class);
    private final MongoPolicyConverter<Policy> policyConverter = mock(MongoPolicyConverter.class);

    private final MongoPolicyRepository<Policy> repository = new MongoPolicyRepository<>(collection, policyConverter);

    @Test
    void shouldSavePolicy() {
        Policy policy = FakePolicyMother.build();
        Bson query = givenConvertsToFindByIdQuery(policy);
        Document document = givenConvertsToMongoDocument(policy);

        repository.save(policy);

        ArgumentCaptor<ReplaceOptions> captor = ArgumentCaptor.forClass(ReplaceOptions.class);
        verify(collection).replaceOne(eq(query), eq(document), captor.capture());
        ReplaceOptions options = captor.getValue();
        assertThat(options.isUpsert()).isTrue();
    }

    @Test
    void shouldLoadPolicyIfPresent() {
        UUID id = UUID.randomUUID();
        Bson query = givenConvertsToFindByIdQuery(id);
        FindIterable<Document> documents = givenDocumentsReturnedForQuery(query);
        Document document = givenHasFirstDocument(documents);
        Policy expectedPolicy = givenConvertsToPolicy(document);

        Optional<Policy> policy = repository.load(id);

        assertThat(policy).contains(expectedPolicy);
    }

    @Test
    void shouldLoadEmptyPolicyIfNotPresent() {
        UUID id = UUID.randomUUID();
        Bson query = givenConvertsToFindByIdQuery(id);
        FindIterable<Document> documents = givenDocumentsReturnedForQuery(query);
        given(documents.first()).willReturn(null);

        Optional<Policy> policy = repository.load(id);

        assertThat(policy).isEmpty();
    }

    @Test
    void shouldLoadAllPolicies() {
        FindIterable<Document> documents = givenAllDocumentsReturned();
        Document document = givenHasDocument(documents);
        Policy expectedPolicy = givenConvertsToPolicy(document);

        Policies<Policy> policies = repository.loadAll();

        assertThat(policies).containsExactly(expectedPolicy);
    }

    @Test
    void shouldDeletePolicyById() {
        UUID id = UUID.randomUUID();
        Bson query = givenConvertsToFindByIdQuery(id);

        repository.delete(id);

        verify(collection).deleteOne(query);
    }

    private Bson givenConvertsToFindByIdQuery(Policy policy) {
        return givenConvertsToFindByIdQuery(policy.getId());
    }

    private Bson givenConvertsToFindByIdQuery(UUID id) {
        Bson query = mock(Bson.class);
        given(policyConverter.toFindByIdQuery(id)).willReturn(query);
        return query;
    }

    private Document givenConvertsToMongoDocument(Policy policy) {
        Document document = mock(Document.class);
        given(policyConverter.toDocument(policy)).willReturn(document);
        return document;
    }

    private FindIterable<Document> givenDocumentsReturnedForQuery(Bson query) {
        FindIterable<Document> documents = mock(FindIterable.class);
        given(collection.find(query)).willReturn(documents);
        return documents;
    }

    private FindIterable<Document> givenAllDocumentsReturned() {
        FindIterable<Document> documents = mock(FindIterable.class);
        given(collection.find()).willReturn(documents);
        return documents;
    }

    private Document givenHasFirstDocument(FindIterable<Document> documents) {
        Document document = mock(Document.class);
        given(documents.first()).willReturn(document);
        return document;
    }

    private Document givenHasDocument(FindIterable<Document> documents) {
        Document document = mock(Document.class);
        MongoCursor<Document> cursor = mock(MongoCursor.class);
        given(cursor.hasNext()).willReturn(true, false);
        given(cursor.next()).willReturn(document);
        given(documents.iterator()).willReturn(cursor);
        return document;
    }

    private Policy givenConvertsToPolicy(Document document) {
        Policy policy = FakePolicyMother.build();
        given(policyConverter.toPolicy(document)).willReturn(policy);
        return policy;
    }

}
