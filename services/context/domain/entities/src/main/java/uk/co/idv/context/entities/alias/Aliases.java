package uk.co.idv.context.entities.alias;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class Aliases implements Iterable<Alias> {

    private final Collection<Alias> aliases;

    public static Aliases empty() {
        return with();
    }

    public static Aliases with(Alias... aliases) {
        return with(Arrays.asList(aliases));
    }

    public static Aliases with(Collection<Alias> aliases) {
        return new Aliases(aliases);
    }

    protected Aliases(Collection<Alias> aliases) {
        this.aliases = new LinkedHashSet<>(aliases);
    }

    @Override
    public Iterator<Alias> iterator() {
        return aliases.iterator();
    }

    public Optional<UUID> getIdvIdValue() {
        return getIdvId().map(IdvId::getValueAsUuid);
    }

    public Optional<IdvId> getIdvId() {
        return getAliasByType(IdvId.TYPE).map(alias -> (IdvId) alias);
    }

    public boolean contains(Alias alias) {
        return aliases.contains(alias);
    }

    public Stream<Alias> stream() {
        return aliases.stream();
    }

    public int size() {
        return aliases.size();
    }

    public void add(Alias alias) {
        aliases.add(alias);
    }

    public void add(Aliases aliasesToAdd) {
        aliasesToAdd.forEach(this::add);
    }

    private Optional<Alias> getAliasByType(String type) {
        return aliases.stream()
                .filter(alias -> alias.isType(type))
                .findFirst();
    }

}
