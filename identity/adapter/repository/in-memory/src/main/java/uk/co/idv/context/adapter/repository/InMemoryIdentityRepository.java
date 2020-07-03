package uk.co.idv.context.adapter.repository;

import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.identity.Identities;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.usecases.identity.IdentityRepository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemoryIdentityRepository implements IdentityRepository {

    private final Map<String, Identity> identities = new ConcurrentHashMap<>();

    @Override
    public void save(Identity updated) {
        Optional<Identity> existing = load(updated.getIdvId());
        existing.ifPresent(value -> deleteEntriesForRemovedAliases(value, updated));
        Collection<String> keys = toKeys(updated);
        keys.forEach(key -> identities.put(key, updated));
    }

    @Override
    public Optional<Identity> load(Alias alias) {
        String key = alias.format();
        return Optional.ofNullable(identities.get(key));
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

    private void deleteEntriesForRemovedAliases(Identity existing, Identity updated) {
        Aliases aliasesToRemove = existing.getAliasesNotPresent(updated);
        delete(aliasesToRemove);
    }

    private void delete(Alias alias) {
        identities.remove(alias.format());
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
