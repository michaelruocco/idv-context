package uk.co.idv.context.adapter.stub.identity.find.data.emailaddress;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.stub.identity.find.data.StubDataFactory;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.entities.emailaddress.EmailAddressesMother;

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
