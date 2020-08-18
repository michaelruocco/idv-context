package uk.co.idv.context.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.BatchGetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableKeysAndAttributes;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.alias.CreditCardNumberMother;
import uk.co.idv.context.entities.alias.DefaultAliasMother;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.identity.Identities;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;

import java.util.Map;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class IdentityConverterTest {

    private static final String TABLE_NAME = "table-name";

    private final Table table = mock(Table.class);
    private final IdentityItemConverter itemConverter = mock(IdentityItemConverter.class);

    private final IdentityConverter converter = new IdentityConverter(table, itemConverter);

    @BeforeEach
    void setUp() {
        given(table.getTableName()).willReturn(TABLE_NAME);
    }

    @Test
    void shouldConvertIdentityToTableWriteItemsForUpdate() {
        Alias idvId = IdvIdMother.idvId();
        Alias creditCardNumber = CreditCardNumberMother.creditCardNumber();
        Identity identity = IdentityMother.withAliases(idvId, creditCardNumber);
        Item idvIdItem = givenAliasConvertedToItem(identity, idvId);
        Item creditCardNumberItem = givenAliasConvertedToItem(identity, creditCardNumber);

        TableWriteItems writeItems = converter.toBatchUpdateItems(identity);

        assertThat(writeItems.getTableName()).isEqualTo(TABLE_NAME);
        assertThat(writeItems.getItemsToPut()).containsExactly(idvIdItem, creditCardNumberItem);
    }

    @Test
    void shouldConvertAliasesToTableWriteItemsForDelete() {
        Alias idvId = IdvIdMother.idvId();
        Alias creditCardNumber = CreditCardNumberMother.creditCardNumber();
        PrimaryKey idvIdKey = givenAliasConvertedToPrimaryKey(idvId);
        PrimaryKey creditCardNumberKey = givenAliasConvertedToPrimaryKey(creditCardNumber);

        TableWriteItems writeItems = converter.toBatchDeleteItems(AliasesMother.with(idvId, creditCardNumber));

        assertThat(writeItems.getTableName()).isEqualTo(TABLE_NAME);
        assertThat(writeItems.getPrimaryKeysToDelete()).containsExactly(idvIdKey, creditCardNumberKey);
    }

    @Test
    void shouldConvertAliasesToPrimaryKeysForLoad() {
        Alias idvId = IdvIdMother.idvId();
        Alias creditCardNumber = CreditCardNumberMother.creditCardNumber();
        PrimaryKey idvIdKey = givenAliasConvertedToPrimaryKey(idvId);
        PrimaryKey creditCardNumberKey = givenAliasConvertedToPrimaryKey(creditCardNumber);

        TableKeysAndAttributes keys = converter.toKeys(AliasesMother.with(idvId, creditCardNumber));

        assertThat(keys.getTableName()).isEqualTo(TABLE_NAME);
        assertThat(keys.getPrimaryKeys()).containsExactly(idvIdKey, creditCardNumberKey);
    }

    @Test
    void shouldConvertBatchGetOutcomeToIdentities() {
        BatchGetItemOutcome outcome = mock(BatchGetItemOutcome.class);
        Item item = new Item();
        given(outcome.getTableItems()).willReturn(Map.of(TABLE_NAME, singletonList(item)));
        Identity expectedIdentity = IdentityMother.example();
        given(itemConverter.toIdentity(item)).willReturn(expectedIdentity);

        Identities identities = converter.toIdentities(outcome);

        assertThat(identities).containsExactly(expectedIdentity);
    }

    @Test
    void shouldConvertAliasToIdentity() {
        BatchGetItemOutcome outcome = mock(BatchGetItemOutcome.class);
        Item item = new Item();
        given(outcome.getTableItems()).willReturn(Map.of(TABLE_NAME, singletonList(item)));
        Identity expectedIdentity = IdentityMother.example();
        given(itemConverter.toIdentity(item)).willReturn(expectedIdentity);

        Identities identities = converter.toIdentities(outcome);

        assertThat(identities).containsExactly(expectedIdentity);
    }

    @Test
    void shouldConvertAliasToItem() {
        Alias alias = DefaultAliasMother.build();
        PrimaryKey key = givenAliasConvertedToPrimaryKey(alias);
        Item expectedItem = givenItemReturnedForPrimaryKey(key);

        Optional<Item> item = converter.toItem(alias);

        assertThat(item).contains(expectedItem);
    }

    @Test
    void shouldConvertItemToIdentity() {
        Item item = new Item();
        Identity expectedIdentity = givenItemConvertedToIdentity(item);

        Identity identity = converter.toIdentity(item);

        assertThat(identity).isEqualTo(expectedIdentity);
    }

    private Item givenAliasConvertedToItem(Identity identity, Alias alias) {
        Item item = new Item();
        given(itemConverter.toItem(identity, alias)).willReturn(item);
        return item;
    }

    private PrimaryKey givenAliasConvertedToPrimaryKey(Alias alias) {
        PrimaryKey key = new PrimaryKey();
        given(itemConverter.toPrimaryKey(alias)).willReturn(key);
        return key;
    }

    private Item givenItemReturnedForPrimaryKey(PrimaryKey key) {
        Item item = new Item();
        given(table.getItem(key)).willReturn(item);
        return item;
    }

    private Identity givenItemConvertedToIdentity(Item item) {
        Identity identity = IdentityMother.example();
        given(itemConverter.toIdentity(item)).willReturn(identity);
        return identity;
    }

}
