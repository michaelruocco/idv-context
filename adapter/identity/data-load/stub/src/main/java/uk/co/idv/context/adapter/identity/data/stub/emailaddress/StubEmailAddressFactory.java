package uk.co.idv.context.adapter.identity.data.stub.emailaddress;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.adapter.identity.data.stub.StubDataFactory;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;

@Slf4j
public class StubEmailAddressFactory implements StubDataFactory<EmailAddresses> {

    @Override
    public String getName() {
        return EmailAddresses.class.getSimpleName();
    }

    @Override
    public EmailAddresses getPopulatedData() {
        return new EmailAddresses(
                "joe.bloggs@hotmail.com",
                "joebloggs@live.co.uk"
        );
    }

    @Override
    public EmailAddresses getEmptyData() {
        return new EmailAddresses();
    }

}
