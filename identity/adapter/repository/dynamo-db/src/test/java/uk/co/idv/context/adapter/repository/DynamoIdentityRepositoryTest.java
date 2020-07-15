package uk.co.idv.context.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.BatchGetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.TableKeysAndAttributes;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.KeysAndAttributes;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.Aliases;
import uk.co.idv.context.entities.alias.AliasesMother;
import uk.co.idv.context.entities.identity.Identities;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;

import java.util.Collections;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DynamoIdentityRepositoryTest {

    private final IdentityConverter converter = mock(IdentityConverter.class);
    private final DynamoDB dynamoDb = mock(DynamoDB.class);

    private final DynamoIdentityRepository repository = new DynamoIdentityRepository(converter, dynamoDb);

    @Test
    void createShouldUpdateItems() {
        Identity identity = IdentityMother.example();
        givenIdentitySuccessfullyUpdated(identity);

        assertThatCode(() -> repository.create(identity)).doesNotThrowAnyException();
    }

    @Test
    void updateShouldOnlyUpdateItemsIfNoAliasesHaveBeenRemoved() {
        Identity updated = IdentityMother.example();
        Identity existing = IdentityMother.example();
        givenIdentitySuccessfullyUpdated(updated);

        assertThatCode(() -> repository.update(updated, existing)).doesNotThrowAnyException();
    }

    @Test
    void updateShouldDeleteItemsForRemovedAliasesAndThenUpdateItems() {
        Identity updated = IdentityMother.withAliases(AliasesMother.idvIdOnly());
        Identity existing = IdentityMother.withAliases(AliasesMother.idvIdAndCreditCardNumber());
        givenAliasesSuccessfullyDeleted(AliasesMother.creditCardNumberOnly());
        givenIdentitySuccessfullyUpdated(updated);

        assertThatCode(() -> repository.update(updated, existing)).doesNotThrowAnyException();
    }

    @Test
    void shouldLoadIdentities() {
        Identity identity = IdentityMother.example();
        Identities expectedIdentities = new Identities(identity);
        givenIdentitiesSuccessfullyLoaded(identity.getAliases(), expectedIdentities);

        Identities identities = repository.load(identity.getAliases());

        assertThat(identities).isEqualTo(expectedIdentities);
    }

    @Test
    void createShouldThrowExceptionIfBatchWriteFails() {
        Identity identity = IdentityMother.example();
        BatchWriteItemOutcome outcome = givenBatchWriteOutcome(identity);
        given(outcome.getUnprocessedItems()).willReturn(Map.of("table-name", Collections.emptyList()));

        DynamoBatchWriteException error = catchThrowableOfType(
                () -> repository.create(identity),
                DynamoBatchWriteException.class
        );

        assertThat(error.getOutcome()).isEqualTo(outcome);
    }

    @Test
    void updateShouldThrowExceptionIfBatchWriteFails() {
        Identity updated = IdentityMother.example();
        Identity existing = IdentityMother.example();
        BatchWriteItemOutcome outcome = givenBatchWriteOutcome(updated);
        given(outcome.getUnprocessedItems()).willReturn(Map.of("table-name", Collections.emptyList()));

        DynamoBatchWriteException error = catchThrowableOfType(
                () -> repository.update(updated, existing),
                DynamoBatchWriteException.class
        );

        assertThat(error.getOutcome()).isEqualTo(outcome);
    }

    @Test
    void deleteShouldThrowExceptionIfBatchDeleteFails() {
        Identity updated = IdentityMother.withAliases(AliasesMother.idvIdOnly());
        Identity existing = IdentityMother.withAliases(AliasesMother.idvIdAndCreditCardNumber());
        BatchWriteItemOutcome outcome = givenBatchDeleteOutcome(AliasesMother.creditCardNumberOnly());
        given(outcome.getUnprocessedItems()).willReturn(Map.of("table-name", Collections.emptyList()));

        DynamoBatchWriteException error = catchThrowableOfType(
                () -> repository.update(updated, existing),
                DynamoBatchWriteException.class
        );

        assertThat(error.getOutcome()).isEqualTo(outcome);
    }

    @Test
    void loadIdentitiesShouldThrowExceptionIfBatchGetFails() {
        Aliases aliases = AliasesMother.idvIdAndCreditCardNumber();
        BatchGetItemOutcome outcome = givenBatchGetOutcome(aliases);
        given(outcome.getUnprocessedKeys()).willReturn(Map.of("table-name", new KeysAndAttributes()));

        DynamoBatchGetException error = catchThrowableOfType(
                () -> repository.load(aliases),
                DynamoBatchGetException.class
        );

        assertThat(error.getOutcome()).isEqualTo(outcome);
    }

    private void givenAliasesSuccessfullyDeleted(Aliases aliases) {
        givenBatchDeleteOutcome(aliases);
    }

    private BatchWriteItemOutcome givenBatchDeleteOutcome(Aliases aliases) {
        TableWriteItems items = mock(TableWriteItems.class);
        given(converter.toBatchDeleteItems(aliases)).willReturn(items);
        BatchWriteItemOutcome outcome = mock(BatchWriteItemOutcome.class);
        given(dynamoDb.batchWriteItem(items)).willReturn(outcome);
        return outcome;
    }

    private void givenIdentitySuccessfullyUpdated(Identity identity) {
        givenBatchWriteOutcome(identity);
    }

    private BatchWriteItemOutcome givenBatchWriteOutcome(Identity identity) {
        TableWriteItems items = mock(TableWriteItems.class);
        given(converter.toBatchUpdateItems(identity)).willReturn(items);
        BatchWriteItemOutcome outcome = mock(BatchWriteItemOutcome.class);
        given(dynamoDb.batchWriteItem(items)).willReturn(outcome);
        return outcome;
    }

    private void givenIdentitiesSuccessfullyLoaded(Aliases aliases, Identities identities) {
        BatchGetItemOutcome outcome = givenBatchGetOutcome(aliases);
        given(converter.toIdentities(outcome)).willReturn(identities);
    }

    private BatchGetItemOutcome givenBatchGetOutcome(Aliases aliases) {
        TableKeysAndAttributes keys = mock(TableKeysAndAttributes.class);
        given(converter.toKeys(aliases)).willReturn(keys);
        BatchGetItemOutcome outcome = mock(BatchGetItemOutcome.class);
        given(dynamoDb.batchGetItem(keys)).willReturn(outcome);
        return outcome;
    }

}
