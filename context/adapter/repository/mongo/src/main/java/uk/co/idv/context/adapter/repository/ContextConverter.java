package uk.co.idv.context.adapter.repository;

import lombok.RequiredArgsConstructor;
import org.bson.BsonTimestamp;
import org.bson.Document;
import org.bson.conversions.Bson;
import uk.co.idv.context.entities.context.Context;
import uk.co.mruoc.json.JsonConverter;

import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

@RequiredArgsConstructor
public class ContextConverter {

    private final JsonConverter converter;

    public Context toContext(Document document) {
        return converter.toObject(document.toJson(), Context.class);
    }

    public Document toDocument(Context context) {
        Document document = Document.parse(converter.toJson(context));
        document.put("_id", context.getId().toString());
        BsonTimestamp timestamp = new BsonTimestamp(context.getCreated().toEpochMilli());
        document.put("timestamp", timestamp);
        return document;
    }

    public Bson toFindByIdQuery(UUID id) {
        return eq("_id", id.toString());
    }

}
