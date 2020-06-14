package uk.co.idv.context.entities.alias;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

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

    public Aliases(Alias... aliases) {
        this(Arrays.asList(aliases));
    }

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

    public Aliases add(Aliases aliasesToAdd) {
        Collection<Alias> updatedAliases = new LinkedHashSet<>(aliases);
        aliasesToAdd.forEach(updatedAliases::add);
        return new Aliases(updatedAliases);
    }

    public Aliases add(Alias alias) {
        if (alias.isIdvId() && hasIdvId()) {
            throw new IdvIdAlreadyPresentException(alias, getIdvId());
        }
        return add(new Aliases(alias));
    }

    public Aliases notPresent(Aliases comparison) {
        return new Aliases(CollectionUtils.subtract(this.aliases, comparison.aliases));
    }

    public int size() {
        return aliases.size();
    }

    public UUID getIdvIdValue() {
        return getIdvId().getValueAsUuid();
    }

    public IdvId getIdvId() {
        return getAliasByType(IdvId.TYPE)
                .map(alias -> (IdvId) alias)
                .orElseThrow(IdvIdNotFoundException::new);
    }

    public boolean hasIdvId() {
        return getAliasByType(IdvId.TYPE).isPresent();
    }

    private Optional<Alias> getAliasByType(String type) {
        return getAliasesByType(type).findFirst();
    }

    private Stream<Alias> getAliasesByType(String type) {
        return aliases.stream().filter(alias -> alias.isType(type));
    }

}
