package uk.co.idv.context.entities.identity;

import com.neovisionaries.i18n.CountryCode;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.phonenumber.PhoneNumbers;

import java.util.UUID;

public interface Identity {

    UUID getIdvIdValue();

    IdvId getIdvId();

    boolean hasAlias(Alias alias);

    PhoneNumbers getPhoneNumbers();

    PhoneNumbers getMobilePhoneNumbers();

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
