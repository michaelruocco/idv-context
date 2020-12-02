package uk.co.idv.identity.adapter.repository.converter;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.adapter.repository.document.IdentityDocument;
import uk.co.idv.identity.entities.alias.AliasFactory;
import uk.co.idv.identity.entities.identity.Identities;
import uk.co.idv.identity.entities.identity.Identity;

import java.util.Collection;
import java.util.LinkedHashSet;

@RequiredArgsConstructor
public class IdentityDocumentsConverter {

    private final IdentityDocumentConverter converter;

    public static IdentityDocumentsConverter build(AliasFactory factory) {
        return new IdentityDocumentsConverter(IdentityDocumentConverter.build(factory));
    }

    public Identities toIdentities(FindIterable<IdentityDocument> documents) {
        Collection<Identity> identities = new LinkedHashSet<>();
        try (MongoCursor<IdentityDocument> cursor = documents.iterator()) {
            while (cursor.hasNext()) {
                identities.add(toIdentity(cursor.next()));
            }
        }
        return new Identities(identities);
    }

    public Identity toIdentity(IdentityDocument document) {
        return converter.toIdentity(document);
    }

    public IdentityDocument toDocument(Identity identity) {
        return converter.toDocument(identity);
    }

}
