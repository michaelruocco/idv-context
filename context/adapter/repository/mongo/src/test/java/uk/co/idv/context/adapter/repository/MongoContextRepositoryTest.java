package uk.co.idv.context.adapter.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;
import uk.co.idv.context.usecases.context.ContextRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MongoContextRepositoryTest {

    private final MongoCollection<Document> collection = mock(MongoCollection.class);
    private final ContextConverter contextConverter = mock(ContextConverter.class);

    private final ContextRepository repository = new MongoContextRepository(collection, contextConverter);

    @Test
    void shouldUseFindByIdQueryWhenSavingContext() {
        Context context = ContextMother.build();
        Bson expectedQuery = givenConvertsToQuery(context);
        Document expectedDocument = givenConvertsToDocument(context);

        repository.save(context);

        ArgumentCaptor<Bson> captor = ArgumentCaptor.forClass(Bson.class);
        verify(collection).replaceOne(captor.capture(), eq(expectedDocument), any(ReplaceOptions.class));
        Bson query = captor.getValue();
        assertThat(query).isEqualTo(expectedQuery);
    }

    @Test
    void shouldPassContextDocumentWhenSavingContext() {
        Context context = ContextMother.build();
        Bson expectedQuery = givenConvertsToQuery(context);
        Document expectedDocument = givenConvertsToDocument(context);

        repository.save(context);

        ArgumentCaptor<Document> captor = ArgumentCaptor.forClass(Document.class);
        verify(collection).replaceOne(eq(expectedQuery), captor.capture(), any(ReplaceOptions.class));
        Document document = captor.getValue();
        assertThat(document).isEqualTo(expectedDocument);
    }

    @Test
    void shouldReplaceOptionsWithUpsertTrueWhenSavingContext() {
        Context context = ContextMother.build();
        Bson expectedQuery = givenConvertsToQuery(context);
        Document expectedDocument = givenConvertsToDocument(context);

        repository.save(context);

        ArgumentCaptor<ReplaceOptions> captor = ArgumentCaptor.forClass(ReplaceOptions.class);
        verify(collection).replaceOne(eq(expectedQuery), eq(expectedDocument), captor.capture());
        ReplaceOptions options = captor.getValue();
        assertThat(options.isUpsert()).isTrue();
    }

    @Test
    void shouldReturnEmptyOptionalIfDocumentNotFound() {
        UUID id = UUID.randomUUID();
        Bson query = givenConvertsToQuery(id);
        FindIterable<Document> documents = givenDocumentsReturnedForQuery(query);
        given(documents.first()).willReturn(null);

        Optional<Context> context = repository.load(id);

        assertThat(context).isEmpty();
    }

    @Test
    void shouldReturnContextIfDocumentFound() {
        UUID id = UUID.randomUUID();
        Bson query = givenConvertsToQuery(id);
        FindIterable<Document> documents = givenDocumentsReturnedForQuery(query);
        Document document = mock(Document.class);
        given(documents.first()).willReturn(document);
        Context expectedContext = ContextMother.build();
        given(contextConverter.toContext(document)).willReturn(expectedContext);

        Optional<Context> context = repository.load(id);

        assertThat(context).contains(expectedContext);
    }

    private Bson givenConvertsToQuery(Context context) {
        return givenConvertsToQuery(context.getId());
    }

    private Bson givenConvertsToQuery(UUID id) {
        Bson query = mock(Bson.class);
        given(contextConverter.toFindByIdQuery(id)).willReturn(query);
        return query;
    }

    private Document givenConvertsToDocument(Context context) {
        Document document = mock(Document.class);
        given(contextConverter.toDocument(context)).willReturn(document);
        return document;
    }

    private FindIterable<Document> givenDocumentsReturnedForQuery(Bson query) {
        FindIterable<Document> documents = mock(FindIterable.class);
        given(collection.find(query)).willReturn(documents);
        return documents;
    }

}
