package uk.co.idv.identity.adapter.repository.converter;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import org.junit.jupiter.api.Test;
import uk.co.idv.identity.adapter.repository.document.IdentityDocument;
import uk.co.idv.identity.adapter.repository.document.IdentityDocumentMother;
import uk.co.idv.identity.entities.identity.Identities;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IdentityDocumentsConverterTest {

    private final IdentityDocumentConverter documentConverter = mock(IdentityDocumentConverter.class);

    private final IdentityDocumentsConverter converter = new IdentityDocumentsConverter(documentConverter);

    @Test
    void shouldConvertSingleDocumentToIdentity() {
        IdentityDocument document = IdentityDocumentMother.example();
        Identity expectedIdentity = IdentityMother.example();
        when(documentConverter.toIdentity(document)).thenReturn(expectedIdentity);

        Identity identity = converter.toIdentity(document);

        assertThat(identity).isEqualTo(expectedIdentity);
    }

    @Test
    void shouldConvertSingleIdentityToDocument() {
        Identity identity = IdentityMother.example();
        IdentityDocument expectedDocument = IdentityDocumentMother.example();
        when(documentConverter.toDocument(identity)).thenReturn(expectedDocument);

        IdentityDocument document = converter.toDocument(identity);

        assertThat(document).isEqualTo(expectedDocument);
    }

    @Test
    void shouldConvertMultipleIdentityDocumentsToIdentities() {
        IdentityDocument document1 = mock(IdentityDocument.class);
        IdentityDocument document2 = mock(IdentityDocument.class);
        FindIterable<IdentityDocument> documents = toFindIterable(document1, document2);
        Identity identity1 = givenConvertsTo(document1);
        Identity identity2 = givenConvertsTo(document2);

        Identities identities = converter.toIdentities(documents);

        assertThat(identities).containsExactly(identity1, identity2);
    }

    private FindIterable<IdentityDocument> toFindIterable(IdentityDocument document1, IdentityDocument document2) {
        MongoCursor<IdentityDocument> cursor = mock(MongoCursor.class);
        given(cursor.hasNext()).willReturn(true, true, false);
        given(cursor.next()).willReturn(document1, document2);
        FindIterable<IdentityDocument> iterable = mock(FindIterable.class);
        given(iterable.iterator()).willReturn(cursor);
        return iterable;
    }

    private Identity givenConvertsTo(IdentityDocument document) {
        Identity identity = mock(Identity.class);
        given(documentConverter.toIdentity(document)).willReturn(identity);
        return identity;
    }

}
