package uk.co.idv.policy.adapter.repository;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.bson.conversions.Bson;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.mruoc.json.JsonConverter;

import java.util.UUID;

import static com.mongodb.client.model.Filters.eq;

//TODO unit test
@RequiredArgsConstructor
public class PolicyConverter<T extends Policy> {

    private final String idFieldName;
    private final Class<T> type;
    private final JsonConverter converter;

    public T toPolicy(Document document) {
        return converter.toObject(document.toJson(), type);
    }

    public Document toDocument(T policy) {
        Document document = Document.parse(converter.toJson(policy));
        document.put(idFieldName, policy.getId().toString());
        return document;
    }

    public Bson toFindByIdQuery(UUID id) {
        return eq(idFieldName, id.toString());
    }

}
