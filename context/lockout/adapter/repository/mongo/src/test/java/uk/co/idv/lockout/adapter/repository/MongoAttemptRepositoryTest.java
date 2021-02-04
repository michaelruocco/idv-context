package uk.co.idv.lockout.adapter.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.alias.IdvIdMother;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.idv.lockout.entities.attempt.AttemptsMother;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class MongoAttemptRepositoryTest {

    private final MongoCollection<Document> collection = mock(MongoCollection.class);
    private final MongoAttemptConverter attemptConverter = mock(MongoAttemptConverter.class);

    private final MongoAttemptRepository repository = MongoAttemptRepository.builder()
            .collection(collection)
            .attemptConverter(attemptConverter)
            .build();

    @Test
    void shouldSaveAttempts() {
        Attempts attempts = AttemptsMother.build();
        Bson query = givenConvertsToFindByIdvIdQuery(attempts);
        Document document = givenConvertsToMongoDocument(attempts);

        repository.save(attempts);

        ArgumentCaptor<ReplaceOptions> captor = ArgumentCaptor.forClass(ReplaceOptions.class);
        verify(collection).replaceOne(eq(query), eq(document), captor.capture());
        ReplaceOptions options = captor.getValue();
        assertThat(options.isUpsert()).isTrue();
    }

    @Test
    void shouldLoadAttemptsIfPresent() {
        IdvId idvId = IdvIdMother.idvId();
        Bson query = givenConvertsToFindByIdvIdQuery(idvId);
        FindIterable<Document> documents = givenDocumentsReturnedForQuery(query);
        Document document = givenHasFirstDocument(documents);
        Attempts expectedAttempts = givenConvertsToAttempts(document);

        Optional<Attempts> attempts = repository.load(idvId);

        assertThat(attempts).contains(expectedAttempts);
    }

    @Test
    void shouldLoadEmptyAttemptsIfNotPresent() {
        IdvId idvId = IdvIdMother.idvId();
        Bson query = givenConvertsToFindByIdvIdQuery(idvId);
        FindIterable<Document> documents = givenDocumentsReturnedForQuery(query);
        given(documents.first()).willReturn(null);

        Optional<Attempts> attempts = repository.load(idvId);

        assertThat(attempts).isEmpty();
    }

    @Test
    void shouldDoNothingOnDeleteIfIdvIdsIsEmpty() {
        Collection<IdvId> idvIds = Collections.emptyList();

        repository.delete(idvIds);

        verify(collection, never()).deleteMany(any(Bson.class));
    }

    @Test
    void shouldDeleteAttemptsByIdvId() {
        Collection<IdvId> idvIds = Collections.singleton(IdvIdMother.idvId());
        Bson query = givenConvertsToFindByIdvIdsQuery(idvIds);

        repository.delete(idvIds);

        verify(collection).deleteMany(query);
    }

    private Bson givenConvertsToFindByIdvIdQuery(Attempts attempts) {
        return givenConvertsToFindByIdvIdQuery(attempts.getIdvId());
    }

    private Bson givenConvertsToFindByIdvIdQuery(IdvId idvId) {
        Bson query = mock(Bson.class);
        given(attemptConverter.toFindByIdvIdQuery(idvId)).willReturn(query);
        return query;
    }

    private Document givenConvertsToMongoDocument(Attempts attempts) {
        Document document = mock(Document.class);
        given(attemptConverter.toDocument(attempts)).willReturn(document);
        return document;
    }

    private FindIterable<Document> givenDocumentsReturnedForQuery(Bson query) {
        FindIterable<Document> documents = mock(FindIterable.class);
        given(collection.find(query)).willReturn(documents);
        return documents;
    }

    private Document givenHasFirstDocument(FindIterable<Document> documents) {
        Document document = mock(Document.class);
        given(documents.first()).willReturn(document);
        return document;
    }

    private Attempts givenConvertsToAttempts(Document document) {
        Attempts attempts = AttemptsMother.build();
        given(attemptConverter.toAttempts(document)).willReturn(attempts);
        return attempts;
    }

    private Bson givenConvertsToFindByIdvIdsQuery(Collection<IdvId> idvIds) {
        Bson query = mock(Bson.class);
        given(attemptConverter.toFindByIdvIdsQuery(idvIds)).willReturn(query);
        return query;
    }

}
