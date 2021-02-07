package uk.co.idv.identity.adapter.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import uk.co.idv.identity.adapter.repository.converter.IdentityDocumentsConverter;
import uk.co.idv.identity.adapter.repository.query.AliasQueryBuilder;
import uk.co.idv.identity.adapter.repository.document.IdentityDocument;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.identity.entities.alias.CardNumberMother;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.identity.Identities;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MongoIdentityRepositoryTest {

    private final MongoCollection<IdentityDocument> collection = mock(MongoCollection.class);
    private final AliasQueryBuilder queryBuilder = mock(AliasQueryBuilder.class);
    private final IdentityDocumentsConverter identityConverter = mock(IdentityDocumentsConverter.class);

    private final MongoIdentityRepository repository = MongoIdentityRepository.builder()
            .collection(collection)
            .queryBuilder(queryBuilder)
            .identityConverter(identityConverter)
            .build();

    @Test
    void shouldReturnEmptyOptionalIfIdentityNotFoundByAlias() {
        Alias alias = CardNumberMother.credit();
        Bson query = givenConvertsToQuery(alias);
        FindIterable<IdentityDocument> documents = givenReturnsDocuments(query);
        given(documents.first()).willReturn(null);

        Optional<Identity> identity = repository.load(alias);

        assertThat(identity).isEmpty();
    }

    @Test
    void shouldLoadIdentityByAlias() {
        Alias alias = CardNumberMother.credit();
        Bson query = givenConvertsToQuery(alias);
        FindIterable<IdentityDocument> documents = givenReturnsDocuments(query);
        IdentityDocument document = givenHasFirstDocument(documents);
        Identity expectedIdentity = givenConvertsToIdentity(document);

        Optional<Identity> identity = repository.load(alias);

        assertThat(identity).contains(expectedIdentity);
    }

    @Test
    void shouldLoadIdentitiesByAliases() {
        Aliases aliases = AliasesMother.idvIdAndCreditCardNumber();
        Bson query = givenConvertsToQuery(aliases);
        FindIterable<IdentityDocument> documents = givenReturnsDocuments(query);
        Identities expectedIdentities = givenConvertsToIdentities(documents);

        Identities identity = repository.load(aliases);

        assertThat(identity).isEqualTo(expectedIdentities);
    }

    @Test
    void shouldCreateIdentity() {
        Identity identity = IdentityMother.example();
        IdentityDocument document = givenConvertsToDocument(identity);

        repository.create(identity);

        verify(collection).insertOne(document);
    }

    @Test
    void shouldUpdateIdentity() {
        Identity updated = IdentityMother.example();
        Identity existing = IdentityMother.example1();
        Bson query = givenConvertsToQuery(updated.getIdvId());
        IdentityDocument document = givenConvertsToDocument(updated);

        repository.update(updated, existing);

        verify(collection).replaceOne(query, document);
    }

    @Test
    void shouldDeleteIdentitiesByAliases() {
        Aliases aliases = AliasesMother.idvIdAndCreditCardNumber();
        Bson query = givenConvertsToQuery(aliases);

        repository.delete(aliases);

        verify(collection).deleteMany(query);
    }

    private Bson givenConvertsToQuery(Alias alias) {
        Bson query = mock(Bson.class);
        given(queryBuilder.toFindByAliasQuery(alias)).willReturn(query);
        return query;
    }

    private Bson givenConvertsToQuery(IdvId idvId) {
        Bson query = mock(Bson.class);
        given(queryBuilder.toFindByIdvIdQuery(idvId)).willReturn(query);
        return query;
    }

    private Bson givenConvertsToQuery(Aliases aliases) {
        Bson query = mock(Bson.class);
        given(queryBuilder.toFindByAliasesQuery(aliases)).willReturn(query);
        return query;
    }

    private FindIterable<IdentityDocument> givenReturnsDocuments(Bson query) {
        FindIterable<IdentityDocument> documents = mock(FindIterable.class);
        given(collection.find(query)).willReturn(documents);
        return documents;
    }

    private IdentityDocument givenHasFirstDocument(FindIterable<IdentityDocument> documents) {
        IdentityDocument document = mock(IdentityDocument.class);
        given(documents.first()).willReturn(document);
        return document;
    }

    private Identity givenConvertsToIdentity(IdentityDocument document) {
        Identity identity = mock(Identity.class);
        given(identityConverter.toIdentity(document)).willReturn(identity);
        return identity;
    }

    private Identities givenConvertsToIdentities(FindIterable<IdentityDocument> documents) {
        Identities identities = mock(Identities.class);
        given(identityConverter.toIdentities(documents)).willReturn(identities);
        return identities;
    }

    private IdentityDocument givenConvertsToDocument(Identity identity) {
        IdentityDocument document = mock(IdentityDocument.class);
        given(identityConverter.toDocument(identity)).willReturn(document);
        return document;
    }

}
