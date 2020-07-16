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
import java.util.stream.Collectors;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class Aliases implements Iterable<Alias> {

    private final Collection<Alias> values;

    public Aliases(Alias... values) {
        this(Arrays.asList(values));
    }

    public Aliases(Collection<Alias> values) {
        this.values = new LinkedHashSet<>(values);
    }

    @Override
    public Iterator<Alias> iterator() {
        return values.iterator();
    }

    public boolean contains(Alias alias) {
        return values.contains(alias);
    }

    public Stream<Alias> stream() {
        return values.stream();
    }

    public Aliases add(Aliases aliasesToAdd) {
        Collection<Alias> updated = new LinkedHashSet<>(values);
        aliasesToAdd.forEach(this::validate);
        aliasesToAdd.forEach(updated::add);
        return new Aliases(updated);
    }

    public Aliases add(Alias alias) {
        validate(alias);
        return add(new Aliases(alias));
    }

    public Aliases remove(Alias alias) {
        Collection<Alias> updated = new LinkedHashSet<>(values);
        updated.remove(alias);
        return new Aliases(updated);
    }

    private void validate(Alias alias) {
        if (!isValid(alias)) {
            throw new IdvIdAlreadyPresentException(getIdvId(), alias);
        }
    }

    private boolean isValid(Alias alias) {
        if (alias.isIdvId() && hasIdvId()) {
            return getIdvId().equals(alias);
        }
        return true;
    }

    public Aliases notPresent(Aliases comparison) {
        return new Aliases(CollectionUtils.subtract(this.values, comparison.values));
    }

    public int size() {
        return values.size();
    }

    public boolean isEmpty() {
        return values.isEmpty();
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

    public Aliases getCreditCardNumbers() {
        Collection<Alias> creditCardNumbers = getAliasesByType(CreditCardNumber.TYPE).collect(Collectors.toList());
        return new Aliases(creditCardNumbers);
    }

    public String getFirstValue() {
        return values.stream().findFirst()
                .map(Alias::getValue)
                .orElseThrow(EmptyAliasesException::new);
    }

    public boolean hasAnyValuesEndingWith(String suffix) {
        return values.stream().anyMatch(alias -> alias.valueEndsWith(suffix));
    }

    private Optional<Alias> getAliasByType(String type) {
        return getAliasesByType(type).findFirst();
    }

    private Stream<Alias> getAliasesByType(String type) {
        return values.stream().filter(alias -> alias.isType(type));
    }

}
