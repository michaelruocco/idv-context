package uk.co.idv.context.usecases.context;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.entities.context.sequence.SequencesRequest;
import uk.co.mruoc.randomvalue.uuid.UuidGenerator;

@RequiredArgsConstructor
public class SequencesRequestFactory {

    private final UuidGenerator uuidGenerator;

    public SequencesRequest toSequencesRequest(ServiceCreateContextRequest request) {
        return SequencesRequest.builder()
                .contextId(uuidGenerator.generate())
                .identity(request.getIdentity())
                .policies(request.getSequencePolicies())
                .build();
    }

}
