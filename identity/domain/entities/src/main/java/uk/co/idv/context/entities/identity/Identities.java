package uk.co.idv.context.entities.identity;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import uk.co.idv.context.entities.alias.Aliases;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class Identities implements Iterable<Identity> {

    private final Collection<Identity> identities;

    @Override
    public Iterator<Identity> iterator() {
        return identities.iterator();
    }

    public Identity getFirst() {
        return IterableUtils.get(identities, 0);
    }

    public int size() {
        return identities.size();
    }

    public Aliases getIdvIds() {
        return new Aliases(identities.stream()
                .map(Identity::getIdvId)
                .collect(Collectors.toList()));
    }

}
