package uk.co.idv.context.entities.identity;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.DefaultAliases;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Identities implements Iterable<Identity> {

    private final Collection<Identity> values;

    public Identities(Identity... values) {
        this(Arrays.asList(values));
    }

    @Override
    public Iterator<Identity> iterator() {
        return values.iterator();
    }

    public Identity getFirst() {
        return IterableUtils.get(values, 0);
    }

    public int size() {
        return values.size();
    }

    public Aliases getIdvIds() {
        return new DefaultAliases(values.stream()
                .map(Identity::getIdvId)
                .collect(Collectors.toList()));
    }

}
