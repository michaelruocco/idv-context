package uk.co.idv.identity.entities.alias;

import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;
import java.util.stream.Stream;

public interface Aliases extends Iterable<Alias> {

    @Override
    Iterator<Alias> iterator();

    boolean containsOneOf(Aliases aliasesToCheck);

    boolean contains(Alias aliasToCheck);

    Stream<Alias> stream();

    Collection<Alias> asCollection();

    Aliases add(Aliases aliasesToAdd);

    Aliases add(Alias alias);

    Aliases remove(Alias alias);

    Aliases notPresent(Aliases comparison);

    int size();

    boolean isEmpty();

    UUID getIdvIdValue();

    IdvId getIdvId();

    boolean hasIdvId();

    Aliases getCreditCardNumbers();

    String getFirstValue();

    boolean hasAnyValuesEndingWith(String suffix);

    Collection<String> getTypes();

    String format();

}
