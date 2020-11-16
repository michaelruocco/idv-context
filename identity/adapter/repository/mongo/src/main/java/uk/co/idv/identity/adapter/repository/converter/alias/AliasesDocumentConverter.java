package uk.co.idv.identity.adapter.repository.converter.alias;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.adapter.repository.type.AliasDocument;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.AliasFactory;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.DefaultAliases;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class AliasesDocumentConverter {

    private final AliasDocumentConverter documentConverter;

    public AliasesDocumentConverter(AliasFactory factory) {
        this(new AliasDocumentConverter(factory));
    }

    public Aliases toAliases(Collection<AliasDocument> documents) {
        Collection<Alias> aliases = documents.stream()
                .map(this::toAlias)
                .collect(Collectors.toList());
        return new DefaultAliases(aliases);
    }

    public Alias toAlias(AliasDocument document) {
        return documentConverter.toAlias(document);
    }

    public Collection<AliasDocument> toDocuments(Aliases aliases) {
        return aliases.stream().map(this::toDocument).collect(Collectors.toList());
    }

    public AliasDocument toDocument(Alias alias) {
        return documentConverter.toDocument(alias);
    }

}
