package uk.co.idv.identity.entities.alias;

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
public class DefaultAliases implements Aliases {

    private final Collection<Alias> values;

    public DefaultAliases(Alias... values) {
        this(Arrays.asList(values));
    }

    public DefaultAliases(Collection<Alias> values) {
        this.values = new LinkedHashSet<>(values);
    }

    @Override
    public Iterator<Alias> iterator() {
        return values.iterator();
    }

    @Override
    public boolean containsOneOf(Aliases aliasesToCheck) {
        return aliasesToCheck.stream().anyMatch(this::contains);
    }

    @Override
    public boolean contains(Alias aliasToCheck) {
        return values.contains(aliasToCheck);
    }

    @Override
    public Stream<Alias> stream() {
        return values.stream();
    }

    @Override
    public Collection<Alias> asCollection() {
        return values;
    }

    @Override
    public Aliases add(Aliases aliasesToAdd) {
        Collection<Alias> updated = new LinkedHashSet<>(values);
        aliasesToAdd.forEach(this::validate);
        aliasesToAdd.forEach(updated::add);
        return new DefaultAliases(updated);
    }

    @Override
    public Aliases add(Alias alias) {
        validate(alias);
        return add(new DefaultAliases(alias));
    }

    @Override
    public DefaultAliases remove(Alias alias) {
        Collection<Alias> updated = new LinkedHashSet<>(values);
        updated.remove(alias);
        return new DefaultAliases(updated);
    }

    @Override
    public Aliases notPresent(Aliases comparison) {
        return new DefaultAliases(CollectionUtils.subtract(this.values, comparison.asCollection()));
    }

    @Override
    public int size() {
        return values.size();
    }

    @Override
    public boolean isEmpty() {
        return values.isEmpty();
    }

    @Override
    public UUID getIdvIdValue() {
        return getIdvId().getValueAsUuid();
    }

    @Override
    public IdvId getIdvId() {
        return getAliasByType(IdvId.TYPE)
                .map(alias -> (IdvId) alias)
                .orElseThrow(IdvIdNotFoundException::new);
    }

    @Override
    public boolean hasIdvId() {
        return getAliasByType(IdvId.TYPE).isPresent();
    }

    @Override
    public Aliases getCreditCardNumbers() {
        Collection<Alias> creditCardNumbers = getAliasesByType(CreditCardNumber.TYPE).collect(Collectors.toList());
        return new DefaultAliases(creditCardNumbers);
    }

    @Override
    public String getFirstValue() {
        return values.stream().findFirst()
                .map(Alias::getValue)
                .orElseThrow(EmptyAliasesException::new);
    }

    @Override
    public boolean hasAnyValuesEndingWith(String suffix) {
        return values.stream().anyMatch(alias -> alias.valueEndsWith(suffix));
    }

    @Override
    public Collection<String> getTypes() {
        return values.stream()
                .map(Alias::getType)
                .collect(Collectors.toSet());
    }

    @Override
    public String format() {
        return values.stream().map(Alias::format).collect(Collectors.joining(","));
    }

    private Optional<Alias> getAliasByType(String type) {
        return getAliasesByType(type).findFirst();
    }

    private Stream<Alias> getAliasesByType(String type) {
        return values.stream().filter(alias -> alias.isType(type));
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

}
