package uk.co.idv.identity.adapter.repository.type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.BsonTimestamp;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberDocument {

    private String value;
    private BsonTimestamp lastUpdated;

}
