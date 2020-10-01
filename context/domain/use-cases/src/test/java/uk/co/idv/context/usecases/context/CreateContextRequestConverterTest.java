package uk.co.idv.context.usecases.context;

import org.junit.jupiter.api.Test;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequestMother;
import uk.co.idv.context.entities.context.sequence.SequencesRequest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class CreateContextRequestConverterTest {

    private final IdGenerator idGenerator = mock(IdGenerator.class);

    private final CreateContextRequestConverter converter = new CreateContextRequestConverter(idGenerator);

    @Test
    void shouldPopulateIdOnSequencesRequest() {
        UUID id = givenGeneratedId();
        ServiceCreateContextRequest contextRequest = ServiceCreateContextRequestMother.build();

        SequencesRequest sequencesRequest = converter.toSequencesRequest(contextRequest);

        assertThat(sequencesRequest.getContextId()).isEqualTo(id);
    }

    @Test
    void shouldPopulateIdentityOnSequencesRequest() {
        ServiceCreateContextRequest contextRequest = ServiceCreateContextRequestMother.build();

        SequencesRequest sequencesRequest = converter.toSequencesRequest(contextRequest);

        assertThat(sequencesRequest.getIdentity()).isEqualTo(contextRequest.getIdentity());
    }

    @Test
    void shouldPopulateSequencePoliciesOnSequencesRequest() {
        ServiceCreateContextRequest contextRequest = ServiceCreateContextRequestMother.build();

        SequencesRequest sequencesRequest = converter.toSequencesRequest(contextRequest);

        assertThat(sequencesRequest.getPolicies()).isEqualTo(contextRequest.getSequencePolicies());
    }

    private UUID givenGeneratedId() {
        UUID id = UUID.randomUUID();
        given(idGenerator.generate()).willReturn(id);
        return id;
    }

}
