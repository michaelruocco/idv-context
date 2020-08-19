package uk.co.idv.context.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.alias.IdvId;
import uk.co.idv.context.entities.alias.IdvIdMother;
import uk.co.idv.context.entities.lockout.attempt.Attempts;
import uk.co.idv.context.entities.lockout.attempt.AttemptsMother;
import uk.co.idv.context.usecases.lockout.attempt.AttemptRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DynamoAttemptRepositoryTest {

    private final AttemptItemConverter converter = mock(AttemptItemConverter.class);
    private final Table table = mock(Table.class);

    private final AttemptRepository repository = DynamoAttemptRepository.builder()
            .converter(converter)
            .table(table)
            .build();

    @Test
    void shouldSaveAttempts() {
        Attempts attempts = AttemptsMother.build();
        Item expectedItem = givenConvertsToItem(attempts);

        repository.save(attempts);

        verify(table).putItem(expectedItem);
    }

    @Test
    void shouldReturnEmptyOptionalIfAttemptsNotFound() {
        IdvId idvId = IdvIdMother.idvId();
        givenConvertsToKey(idvId);

        Optional<Attempts> attempts = repository.load(idvId);

        assertThat(attempts).isEmpty();
    }

    @Test
    void shouldReturnAttempts() {
        IdvId idvId = IdvIdMother.idvId();
        PrimaryKey key = givenConvertsToKey(idvId);
        Item item = givenKeyLoadsItem(key);
        Attempts expectedAttempts = givenConvertsToAttempts(item);

        Optional<Attempts> attempts = repository.load(idvId);

        assertThat(attempts).contains(expectedAttempts);
    }

    private Item givenConvertsToItem(Attempts attempts) {
        Item item = new Item();
        given(converter.toItem(attempts)).willReturn(item);
        return item;
    }

    private PrimaryKey givenConvertsToKey(IdvId idvId) {
        PrimaryKey key = new PrimaryKey();
        given(converter.toPrimaryKey(idvId)).willReturn(key);
        return key;
    }

    private Item givenKeyLoadsItem(PrimaryKey key) {
        Item item = new Item();
        given(table.getItem(key)).willReturn(item);
        return item;
    }

    private Attempts givenConvertsToAttempts(Item item) {
        Attempts attempts = AttemptsMother.build();
        given(converter.toAttempts(item)).willReturn(attempts);
        return attempts;
    }

}
