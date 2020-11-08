package uk.co.idv.identity.adapter.repository;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import uk.co.idv.identity.entities.identity.Identities;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.mruoc.json.JsonConverter;

import java.util.Collection;
import java.util.LinkedHashSet;

@RequiredArgsConstructor
@Slf4j
public class MongoIdentityConverter {

    private final JsonConverter converter;

    public Identities toIdentities(FindIterable<Document> documents) {
        Collection<Identity> identities = new LinkedHashSet<>();
        try (MongoCursor<Document> cursor = documents.iterator()) {
            while (cursor.hasNext()) {
                identities.add(toIdentity(cursor.next()));
            }
        }
        log.debug("converted {} documents to identities", identities.size());
        return new Identities(identities);
    }

    public Identity toIdentity(Document document) {
        return converter.toObject(document.toJson(), Identity.class);
    }

    public Document toDocument(Identity identity) {
        Document document = Document.parse(converter.toJson(identity));
        document.put("_id", identity.getIdvIdValue().toString());
        return document;
    }

}
