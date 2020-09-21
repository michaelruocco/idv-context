package uk.co.idv.context.adapter.repository;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.Table;
import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;
import uk.co.idv.context.usecases.context.ContextRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DynamoAttemptRepositoryTest {

    private final ContextItemConverter converter = mock(ContextItemConverter.class);
    private final Table table = mock(Table.class);

    private final ContextRepository repository = DynamoContextRepository.builder()
            .converter(converter)
            .table(table)
            .build();

    @Test
    void shouldSaveContext() {
        Context context = ContextMother.build();
        Item expectedItem = givenConvertsToItem(context);

        repository.save(context);

        verify(table).putItem(expectedItem);
    }

    @Test
    void shouldReturnEmptyOptionalIfContextNotFound() {
        UUID id = UUID.randomUUID();
        givenConvertsToKey(id);

        Optional<Context> context = repository.load(id);

        assertThat(context).isEmpty();
    }

    @Test
    void shouldReturnAttempts() {
        UUID id = UUID.randomUUID();
        PrimaryKey key = givenConvertsToKey(id);
        Item item = givenKeyLoadsItem(key);
        Context expectedContext = givenConvertsToContext(item);

        Optional<Context> context = repository.load(id);

        assertThat(context).contains(expectedContext);
    }

    private Item givenConvertsToItem(Context context) {
        Item item = new Item();
        given(converter.toItem(context)).willReturn(item);
        return item;
    }

    private PrimaryKey givenConvertsToKey(UUID id) {
        PrimaryKey key = new PrimaryKey();
        given(converter.toPrimaryKey(id)).willReturn(key);
        return key;
    }

    private Item givenKeyLoadsItem(PrimaryKey key) {
        Item item = new Item();
        given(table.getItem(key)).willReturn(item);
        return item;
    }

    private Context givenConvertsToContext(Item item) {
        Context context = ContextMother.build();
        given(converter.toContext(item)).willReturn(context);
        return context;
    }

}
