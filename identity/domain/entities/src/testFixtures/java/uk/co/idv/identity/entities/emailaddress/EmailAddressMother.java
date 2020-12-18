package uk.co.idv.identity.entities.emailaddress;

public interface EmailAddressMother {

    static EmailAddress bugsBunny() {
        return of("bugs.bunny@sky.co.uk");
    }

    static EmailAddress joeBloggsHotmail() {
        return of("joe.bloggs@hotmail.co.uk");
    }

    static EmailAddress joeBloggsYahoo() {
        return of("joebloggs@yahoo.co.uk");
    }

    static EmailAddress of(String value) {
        return new EmailAddress(value);
    }

}
