package uk.co.idv.identity.adapter.repository.converter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.adapter.repository.document.AliasDocument;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.AliasFactory;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.DefaultAliases;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class AliasDocumentConverter {

    private final AliasFactory factory;

    public Aliases toAliases(Collection<AliasDocument> documents) {
        Collection<Alias> aliases = documents.stream()
                .map(this::toAlias)
                .collect(Collectors.toList());
        return new DefaultAliases(aliases);
    }

    public Alias toAlias(AliasDocument document) {
        return factory.build(document.getType(), document.getValue());
    }

    public Collection<AliasDocument> toDocuments(Aliases aliases) {
        return aliases.stream().map(this::toDocument).collect(Collectors.toList());
    }

    public AliasDocument toDocument(Alias alias) {
        return AliasDocument.builder()
                .type(alias.getType())
                .value(alias.getValue())
                .build();
    }

}
