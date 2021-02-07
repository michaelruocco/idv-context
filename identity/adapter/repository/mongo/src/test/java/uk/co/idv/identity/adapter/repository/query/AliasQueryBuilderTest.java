package uk.co.idv.identity.adapter.repository.query;

import org.bson.conversions.Bson;
import org.junit.jupiter.api.Test;
import uk.co.idv.identity.entities.alias.Alias;
import uk.co.idv.identity.entities.alias.Aliases;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.identity.entities.alias.CardNumberMother;
import uk.co.idv.identity.entities.alias.IdvId;
import uk.co.idv.identity.entities.alias.IdvIdMother;

import static org.assertj.core.api.Assertions.assertThat;

class AliasQueryBuilderTest {

    private final AliasQueryBuilder builder = new AliasQueryBuilder();

    @Test
    void shouldBuildFindByAliasesQuery() {
        Aliases aliases = AliasesMother.idvIdAndCreditCardNumber();

        Bson query = builder.toFindByAliasesQuery(aliases);

        assertThat(query).hasToString("Or Filter{filters=[" +
                "Filter{fieldName='_id', value=90b585c6-170f-42a6-ac7c-83d294bdab3f}, " +
                "And " +
                "Filter{filters=[" +
                "Filter{fieldName='aliases.value', value=4929111111111111}, " +
                "Filter{fieldName='aliases.type', value=credit-card-number}]}" +
                "]}");
    }

    @Test
    void shouldBuildFindByAliasQuery() {
        Alias alias = CardNumberMother.credit();

        Bson query = builder.toFindByAliasQuery(alias);

        assertThat(query).hasToString("And Filter{filters=[" +
                "Filter{fieldName='aliases.value', value=4929111111111111}, " +
                "Filter{fieldName='aliases.type', value=credit-card-number}" +
                "]}");
    }

    @Test
    void shouldBuildFindByIdvIdQuery() {
        IdvId idvId = IdvIdMother.idvId();

        Bson query = builder.toFindByIdvIdQuery(idvId);

        assertThat(query).hasToString("Filter" +
                "{fieldName='_id', value=90b585c6-170f-42a6-ac7c-83d294bdab3f}");
    }

    @Test
    void shouldBuildFindByIdvIdQueryIfIdvIdPassedToFindByAlias() {
        IdvId idvId = IdvIdMother.idvId();

        Bson query = builder.toFindByAliasQuery(idvId);

        assertThat(query).hasToString("Filter" +
                "{fieldName='_id', value=90b585c6-170f-42a6-ac7c-83d294bdab3f}");
    }

}
