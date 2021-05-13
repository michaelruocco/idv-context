package uk.co.idv.identity.adapter.repository.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonId;

import java.util.Collection;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdentityDocument {

    @BsonId
    private String id;
    private String country;
    private Collection<AliasDocument> aliases;
    private Collection<String> emailAddresses;
    private Collection<PhoneNumberDocument> phoneNumbers;
    private Collection<MobileDeviceDocument> mobileDevices;

}
