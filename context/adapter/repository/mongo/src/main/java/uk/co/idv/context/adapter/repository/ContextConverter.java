package uk.co.idv.context.adapter.repository;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.conversions.Bson;
import uk.co.idv.context.entities.context.Context;
import uk.co.mruoc.json.JsonConverter;

import java.util.Date;
import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;
import static uk.co.idv.context.adapter.repository.ContextCollection.ID_FIELD_NAME;
import static uk.co.idv.context.adapter.repository.ContextCollection.TTL_INDEX_NAME;

@RequiredArgsConstructor
//TODO unit tests
public class ContextConverter {

    private final JsonConverter converter;

    public Context toContext(Document document) {
        return converter.toObject(document.toJson(), Context.class);
    }

    public Document toDocument(Context context) {
        Document document = Document.parse(converter.toJson(context));
        document.put(ID_FIELD_NAME, context.getId().toString());
        document.put(TTL_INDEX_NAME, new Date(context.getExpiry().toEpochMilli()));
        return document;
    }

    public Bson toFindByIdQuery(UUID id) {
        return eq(ID_FIELD_NAME, id.toString());
    }

}
