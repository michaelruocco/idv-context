package uk.co.idv.context.adapter.repository;

import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryIdentityRepository implements IdentityRepository {

    private final Map<String, Identity> identities = new HashMap<>();

    @Override
    public void save(Identity updated) {
        Optional<Identity> existing = load(updated.getIdvId());
        existing.ifPresent(value -> removeDeletedAliasEntries(value, updated));
        Collection<String> keys = toKeys(updated);
        keys.forEach(key -> identities.put(key, updated));
    }

    @Override
    public void delete(Alias alias) {
        identities.remove(alias.format());
    }

    @Override
    public Optional<Identity> load(Alias alias) {
        String key = alias.format();
        return Optional.ofNullable(identities.get(key));
    }

    @Override
    public Collection<Identity> load(Aliases aliases) {
        return aliases.stream().map(this::load)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .distinct()
                .collect(Collectors.toList());
    }

    private void removeDeletedAliasEntries(Identity existing, Identity updated) {
        Aliases aliasesToRemove = existing.getAliasesNotPresent(updated);
        Collection<String> keysToRemove = toKeys(aliasesToRemove);
        keysToRemove.forEach(identities::remove);
    }

    private static Collection<String> toKeys(Identity identity) {
        Aliases aliases = identity.getAliases();
        return toKeys(aliases);
    }

    private static Collection<String> toKeys(Aliases aliases) {
        return aliases.stream()
                .map(Alias::format)
                .collect(Collectors.toList());
    }

}
