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

    public UUID getIdvIdValue() {
        return aliases.getIdvIdValue();
    }

    public IdvId getIdvId() {
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

    public boolean hasIdvId() {
        return aliases.hasIdvId();
    }

    public Identity setIdvId(IdvId idvId) {
        return copy()
                .aliases(aliases.add(idvId))
                .build();
    }

    public Identity removeIdvId() {
        return copy()
                .aliases(aliases.remove(getIdvId()))
                .build();
    }

    public Identity addData(Identities others) {
        Identity merged = copy().build();
        for (Identity existing : others) {
            merged = merged.addData(existing.removeIdvId());
        }
        return merged;
    }

    public Identity addData(Identity other) {
        validateHasSameCountry(other);
        return Identity.builder()
                .aliases(aliases.add(other.getAliases()))
                .country(country)
                .phoneNumbers(phoneNumbers.add(other.getPhoneNumbers()))
                .emailAddresses(emailAddresses.add(other.getEmailAddresses()))
                .build();
    }

    public boolean hasCountry() {
        return country != null;
    }

    private void validateHasSameCountry(Identity other) {
        if (!hasSameCountry(other)) {
            throw new CountryMismatchException(country, other.getCountry());
        }
    }

    private boolean hasSameCountry(Identity other) {
        return country == other.getCountry();
    }

    private IdentityBuilder copy() {
        return Identity.builder()
                .aliases(aliases)
                .country(country)
                .phoneNumbers(phoneNumbers)
                .emailAddresses(emailAddresses);
    }

}
