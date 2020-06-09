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
    public void save(final Identity updated) {
        final Optional<Identity> existing = load(updated.getIdvId());
        existing.ifPresent(value -> removeDeletedAliasEntries(value, updated));
        final Collection<String> keys = toKeys(updated);
        keys.forEach(key -> identities.put(key, updated));
    }

    @Override
    public Optional<Identity> load(final Alias alias) {
        final String key = toKey(alias);
        return Optional.ofNullable(identities.get(key));
    }

    private void removeDeletedAliasEntries(final Identity existing, final Identity updated) {
        final Aliases aliasesToRemove = existing.getAliasesNotPresent(updated);
        final Collection<String> keysToRemove = toKeys(aliasesToRemove);
        keysToRemove.forEach(identities::remove);
    }

    private static Collection<String> toKeys(final Identity identity) {
        final Aliases aliases = identity.getAliases();
        return toKeys(aliases);
    }

    private static Collection<String> toKeys(final Aliases aliases) {
        return aliases.stream()
                .map(InMemoryIdentityRepository::toKey)
                .collect(Collectors.toList());
    }

    private static String toKey(final Alias alias) {
        return String.format("%s-%s", alias.getType(), alias.getValue());
    }

}
