package uk.co.idv.context.entities.identity;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import lombok.Data;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;

import java.util.UUID;

@Builder
@Data
public class Identity {

    private final CountryCode country;
    private final Aliases aliases;
    private final PhoneNumbers phoneNumbers;
    private final EmailAddresses emailAddresses;

    public Identity setIdvId(IdvId idvId) {
        if (aliases.containsIdvId()) {
            throw new RuntimeException("identity can only have one idvId");
        }
        return Identity.builder()
                .country(country)
                .aliases(aliases.add(idvId))
                .phoneNumbers(phoneNumbers)
                .emailAddresses(emailAddresses)
                .build();
    }
    public UUID getIdvIdValue() {
        return aliases.getIdvIdValue();
    }

    public Alias getIdvId() {
        return aliases.getIdvId();
    }

    public boolean hasAlias(Alias alias) {
        return aliases.contains(alias);
    }

    public PhoneNumbers getMobilePhoneNumbers() {
        return phoneNumbers.getMobileNumbers();
    }

    public Aliases getAliasesNotPresent(Identity other) {
        return aliases.notPresent(other.getAliases());
    }

}
