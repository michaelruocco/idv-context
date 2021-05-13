package uk.co.idv.identity.entities.identity;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import lombok.Data;
import lombok.With;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.mobiledevice.MobileDevices;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

import java.util.UUID;

@Builder(toBuilder = true)
@Data
public class DefaultIdentity implements Identity {

    private final CountryCode country;
    private final Aliases aliases;

    @With
    private final PhoneNumbers phoneNumbers;

    @With
    private final EmailAddresses emailAddresses;

    @With
    private final MobileDevices mobileDevices;

    @Override
    public UUID getIdvIdValue() {
        return aliases.getIdvIdValue();
    }

    @Override
    public IdvId getIdvId() {
        return aliases.getIdvId();
    }

    @Override
    public boolean hasAlias(Alias alias) {
        return aliases.contains(alias);
    }

    @Override
    public Aliases getAliasesNotPresent(Identity other) {
        return aliases.notPresent(other.getAliases());
    }

    @Override
    public boolean hasIdvId() {
        return aliases.hasIdvId();
    }

    @Override
    public DefaultIdentity setIdvId(IdvId idvId) {
        return toBuilder()
                .aliases(aliases.add(idvId))
                .build();
    }

    @Override
    public DefaultIdentity removeIdvId() {
        return toBuilder()
                .aliases(aliases.remove(getIdvId()))
                .build();
    }

    @Override
    public Identity addData(Identities others) {
        Identity merged = toBuilder().build();
        for (Identity existing : others) {
            merged = merged.addData(existing.removeIdvId());
        }
        return merged;
    }

    @Override
    public Identity addData(Identity other) {
        validateHasSameCountry(other);
        return toBuilder()
                .aliases(aliases.add(other.getAliases()))
                .phoneNumbers(phoneNumbers.add(other.getPhoneNumbers()))
                .emailAddresses(emailAddresses.add(other.getEmailAddresses()))
                .mobileDevices(mobileDevices.add(other.getMobileDevices()))
                .build();
    }

    @Override
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

}
