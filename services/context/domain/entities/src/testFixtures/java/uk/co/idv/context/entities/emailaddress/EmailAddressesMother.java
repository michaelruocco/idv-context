package uk.co.idv.context.entities.emailaddress;

import java.util.Arrays;

public interface EmailAddressesMother {

    static EmailAddresses empty() {
        return with();
    }

    static EmailAddresses build() {
        return with("joe.bloggs@hotmail.com", "bugs.bunny@live.co.uk");
    }

    static EmailAddresses with(String... values) {
        return new EmailAddresses(Arrays.asList(values));
    }

}
