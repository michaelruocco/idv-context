package uk.co.idv.identity.adapter.repository.converter.alias;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uk.co.idv.identity.adapter.repository.document.AliasDocument;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.AliasFactory;

@RequiredArgsConstructor
@Slf4j
public class AliasDocumentConverter {

    private final AliasFactory factory;

    public Alias toAlias(AliasDocument document) {
        return factory.build(document.getType(), document.getValue());
    }

    public AliasDocument toDocument(Alias alias) {
        return AliasDocument.builder()
                .type(alias.getType())
                .value(alias.getValue())
                .build();
    }

}
