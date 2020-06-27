package uk.co.idv.context.entities.emailaddress;

public interface EmailAddressesMother {

    static EmailAddresses empty() {
        return with();
    }

    static EmailAddresses one() {
        return with("bugs.bunny@sky.co.uk");
    }

    static EmailAddresses two() {
        return with("joe.bloggs@hotmail.co.uk", "joebloggs@yahoo.co.uk");
    }

    static EmailAddresses with(String... values) {
        return new EmailAddresses(values);
    }

}
