package uk.co.idv.identity.adapter.repository;

import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.identity.Identities;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.usecases.identity.IdentityRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryIdentityRepository implements IdentityRepository {

    private final Map<String, Identity> identities = new ConcurrentHashMap<>();

    @Override
    public void create(Identity updated) {
        batchUpdate(updated);
    }

    @Override
    public void update(Identity updated, Identity existing) {
        deleteEntriesForRemovedAliases(updated, existing);
        batchUpdate(updated);
    }

    @Override
    public Identities load(Aliases aliases) {
        return new Identities(aliases.stream().map(this::load)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .distinct()
                .collect(Collectors.toList()));
    }

    @Override
    public void delete(Aliases aliases) {
        aliases.forEach(this::delete);
    }

    @Override
    public Optional<Identity> load(Alias alias) {
        String key = alias.format();
        return Optional.ofNullable(identities.get(key));
    }

    private void deleteEntriesForRemovedAliases(Identity updated, Identity existing) {
        var aliasesToRemove = existing.getAliasesNotPresent(updated);
        delete(aliasesToRemove);
    }

    private void delete(Alias alias) {
        identities.remove(alias.format());
    }

    private static Collection<String> toKeys(Identity identity) {
        var aliases = identity.getAliases();
        return toKeys(aliases);
    }

    private static Collection<String> toKeys(Aliases aliases) {
        return aliases.stream()
                .map(Alias::format)
                .collect(Collectors.toList());
    }

    private void batchUpdate(Identity updated) {
        Collection<String> keys = toKeys(updated);
        keys.forEach(key -> identities.put(key, updated));
    }

}
