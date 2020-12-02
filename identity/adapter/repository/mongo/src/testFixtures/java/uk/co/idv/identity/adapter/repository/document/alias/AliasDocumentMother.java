package uk.co.idv.identity.adapter.repository.document.alias;

import uk.co.idv.identity.adapter.repository.document.AliasDocument;

import java.util.Arrays;
import java.util.Collection;

public interface AliasDocumentMother {

    static Collection<AliasDocument> idvIdAndCreditCardNumber() {
        return Arrays.asList(idvId(), creditCardNumber());
    }

    static AliasDocument idvId() {
        return AliasDocument.builder()
                .type("idv-id")
                .value("90b585c6-170f-42a6-ac7c-83d294bdab3f")
                .build();
    }

    static AliasDocument creditCardNumber() {
        return AliasDocument.builder()
                .type("credit-card-number")
                .value("4929111111111111")
                .build();
    }

}
