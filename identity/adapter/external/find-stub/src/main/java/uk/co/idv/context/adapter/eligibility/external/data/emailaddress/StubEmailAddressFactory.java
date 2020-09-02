package uk.co.idv.context.adapter.eligibility.external.data.emailaddress;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.eligibility.external.data.StubDataFactory;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;
import uk.co.idv.identity.entities.emailaddress.EmailAddressesMother;

@Slf4j
public class StubEmailAddressFactory implements StubDataFactory<EmailAddresses> {

    @Override
    public String getName() {
        return EmailAddresses.class.getSimpleName();
    }

    @Override
    public EmailAddresses getPopulatedData() {
        return EmailAddressesMother.two();
    }

    @Override
    public EmailAddresses getEmptyData() {
        return EmailAddressesMother.empty();
    }

}
