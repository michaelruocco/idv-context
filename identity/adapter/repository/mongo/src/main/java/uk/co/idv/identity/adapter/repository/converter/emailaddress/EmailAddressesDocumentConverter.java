package uk.co.idv.identity.adapter.repository.converter.emailaddress;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.entities.emailaddress.EmailAddresses;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class EmailAddressesDocumentConverter {

    public EmailAddresses toEmailAddresses(Collection<String> values) {
        return new EmailAddresses(values);
    }

    public Collection<String> toDocuments(EmailAddresses emailAddresses) {
        return emailAddresses.stream().collect(Collectors.toList());
    }

}
