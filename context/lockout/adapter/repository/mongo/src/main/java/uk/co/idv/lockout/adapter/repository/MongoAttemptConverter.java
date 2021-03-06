package uk.co.idv.lockout.adapter.repository;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.conversions.Bson;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.lockout.entities.attempt.Attempts;
import uk.co.mruoc.json.JsonConverter;

import java.util.Collection;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.or;
import static uk.co.idv.lockout.adapter.repository.MongoAttemptCollection.ID_FIELD_NAME;

@RequiredArgsConstructor
public class MongoAttemptConverter {

    private final JsonConverter converter;

    public Attempts toAttempts(Document document) {
        return converter.toObject(document.toJson(), Attempts.class);
    }

    public Document toDocument(Attempts attempts) {
        Document document = Document.parse(converter.toJson(attempts));
        document.put(ID_FIELD_NAME, attempts.getIdvId().getValue());
        return document;
    }

    public Bson toFindByIdvIdsQuery(Collection<IdvId> idvIds) {
        return or(idvIds.stream().map(this::toFindByIdvIdQuery).toArray(Bson[]::new));
    }

    public Bson toFindByIdvIdQuery(IdvId idvId) {
        return eq(ID_FIELD_NAME, idvId.getValue());
    }

}
