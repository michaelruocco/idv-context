package uk.co.idv.identity.adapter.repository.converter;

import org.bson.BsonTimestamp;

import java.time.Instant;

public class InstantConverter {

    public BsonTimestamp toTimestamp(Instant instant) {
        return new BsonTimestamp(instant.toEpochMilli());
    }

    public Instant toInstant(BsonTimestamp timestamp) {
        return Instant.ofEpochMilli(timestamp.getValue());
    }

}
