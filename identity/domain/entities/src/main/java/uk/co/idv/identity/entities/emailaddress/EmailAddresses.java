package uk.co.idv.identity.entities.emailaddress;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.stream.Stream;

@ToString
@EqualsAndHashCode
public class EmailAddresses implements Iterable<EmailAddress> {

    private final Collection<EmailAddress> values;

    public EmailAddresses(EmailAddress... values) {
        this(Arrays.asList(values));
    }

    public EmailAddresses(Collection<EmailAddress> values) {
        this.values = new LinkedHashSet<>(values);
    }

    @Override
    public Iterator<EmailAddress> iterator() {
        return values.iterator();
    }

    public Stream<EmailAddress> stream() {
        return values.stream();
    }

    public boolean isEmpty() {
        return values.isEmpty();
    }

    public EmailAddresses add(EmailAddresses others) {
        Collection<EmailAddress> mergedValues = new LinkedHashSet<>();
        mergedValues.addAll(this.values);
        mergedValues.addAll(others.values);
        return new EmailAddresses(mergedValues);
    }

}
