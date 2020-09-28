package uk.co.idv.context.usecases.context;

import lombok.RequiredArgsConstructor;
import uk.co.idv.common.usecases.id.IdGenerator;
import uk.co.idv.context.entities.context.create.DefaultCreateContextRequest;
import uk.co.idv.context.entities.context.sequence.SequencesRequest;

@RequiredArgsConstructor
public class CreateContextRequestConverter {

    private final IdGenerator idGenerator;

    public SequencesRequest toSequencesRequest(DefaultCreateContextRequest request) {
        return SequencesRequest.builder()
                .contextId(idGenerator.generate())
                .identity(request.getIdentity())
                .policies(request.getSequencePolicies())
                .build();
    }

}
