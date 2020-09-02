package uk.co.idv.identity.entities.identity;

import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.phonenumber.PhoneNumbers;

import java.util.UUID;

public interface Identity {

    UUID getIdvIdValue();

    IdvId getIdvId();

    boolean hasAlias(Alias alias);

    PhoneNumbers getPhoneNumbers();

    Aliases getAliases();

    Aliases getAliasesNotPresent(Identity other);

    EmailAddresses getEmailAddresses();

    boolean hasIdvId();

    Identity setIdvId(IdvId idvId);

    Identity removeIdvId();

    Identity addData(Identities others);

    Identity addData(Identity other);

    CountryCode getCountry();

    boolean hasCountry();

}
