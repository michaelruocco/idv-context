package uk.co.idv.context.usecases.identity.data.stubbed.emailaddress;

import lombok.extern.slf4j.Slf4j;
import uk.co.idv.context.entities.emailaddress.EmailAddresses;
import uk.co.idv.context.usecases.identity.data.stubbed.StubbedDataFactory;

@Slf4j
public class StubbedEmailAddressFactory implements StubbedDataFactory<EmailAddresses> {

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
