package uk.co.idv.identity.adapter.repository.converter;

import org.junit.jupiter.api.Test;
import uk.co.idv.identity.adapter.repository.document.AliasDocument;
import uk.co.idv.identity.adapter.repository.document.AliasDocumentMother;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.AliasFactory;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.identity.entities.alias.CreditCardNumberMother;
import uk.co.idv.identity.entities.alias.IdvIdMother;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class AliasDocumentConverterTest {

    private final AliasFactory aliasFactory = mock(AliasFactory.class);

    private final AliasDocumentConverter converter = new AliasDocumentConverter(aliasFactory);

    @Test
    void shouldConvertAliasToAliasDocument() {
        Alias alias = CreditCardNumberMother.creditCardNumber();

        AliasDocument document = converter.toDocument(alias);

        assertThat(document).isEqualTo(AliasDocumentMother.creditCardNumber());
    }

    @Test
    void shouldConvertMultipleAliasesToAliasDocuments() {
        Aliases aliases = AliasesMother.idvIdAndCreditCardNumber();

        Collection<AliasDocument> document = converter.toDocuments(aliases);

        assertThat(document).containsExactly(
                AliasDocumentMother.idvId(),
                AliasDocumentMother.creditCardNumber()
        );
    }

    @Test
    void shouldConvertAliasDocumentToAlias() {
        AliasDocument document = AliasDocumentMother.creditCardNumber();
        Alias expectedAlias = CreditCardNumberMother.creditCardNumber();
        given(aliasFactory.build(document.getType(), document.getValue())).willReturn(expectedAlias);

        Alias alias = converter.toAlias(document);

        assertThat(alias).isEqualTo(expectedAlias);
    }

    @Test
    void shouldConvertMultipleAliasDocumentsToAliases() {
        AliasDocument document1 = AliasDocumentMother.idvId();
        AliasDocument document2 = AliasDocumentMother.creditCardNumber();
        Alias expectedAlias1 = IdvIdMother.idvId();
        Alias expectedAlias2 = CreditCardNumberMother.creditCardNumber();
        given(aliasFactory.build(document1.getType(), document1.getValue())).willReturn(expectedAlias1);
        given(aliasFactory.build(document2.getType(), document2.getValue())).willReturn(expectedAlias2);

        Aliases aliases = converter.toAliases(Arrays.asList(document1, document2));

        assertThat(aliases).containsExactly(
                expectedAlias1,
                expectedAlias2
        );
    }

}
