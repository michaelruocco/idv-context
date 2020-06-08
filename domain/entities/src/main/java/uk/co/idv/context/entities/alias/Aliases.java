package uk.co.idv.context.entities.alias;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.UUID;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class Aliases implements Iterable<Alias> {

    private final Collection<Alias> aliases;

    public Aliases(Collection<Alias> aliases) {
        this.aliases = new LinkedHashSet<>(aliases);
    }

    @Override
    public Iterator<Alias> iterator() {
        return aliases.iterator();
    }

    public boolean contains(Alias alias) {
        return aliases.contains(alias);
    }

    public Stream<Alias> stream() {
        return aliases.stream();
    }

    public void add(Alias alias) {
        aliases.add(alias);
    }

    public void add(Aliases aliasesToAdd) {
        aliasesToAdd.stream().forEach(this::add);
    }

    public int size() {
        return aliases.size();
    }

    public UUID getIdvIdValue() {
        return getIdvId().getValueAsUuid();
    }

    public IdvId getIdvId() {
        return getAliasesByType(IdvId.TYPE)
                .findFirst()
                .map(alias -> (IdvId) alias)
                .orElseThrow(IdvIdNotFoundException::new);
    }

    private Stream<Alias> getAliasesByType(String type) {
        return aliases.stream().filter(alias -> alias.isType(type));
    }

}
