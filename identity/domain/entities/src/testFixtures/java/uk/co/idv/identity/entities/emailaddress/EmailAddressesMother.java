package uk.co.idv.identity.entities.emailaddress;

import java.util.Arrays;
import java.util.stream.Collectors;

public interface EmailAddressesMother {

    static EmailAddresses empty() {
        return new EmailAddresses();
    }

    static EmailAddresses one() {
        return with(EmailAddressMother.bugsBunny());
    }

    static EmailAddresses two() {
        return with(EmailAddressMother.joeBloggsHotmail(), EmailAddressMother.joeBloggsYahoo());
    }

    static EmailAddresses with(String... values) {
        return new EmailAddresses(Arrays.stream(values).map(EmailAddressMother::of).collect(Collectors.toList()));
    }

    static EmailAddresses with(EmailAddress... addresses) {
        return new EmailAddresses(addresses);
    }

}
