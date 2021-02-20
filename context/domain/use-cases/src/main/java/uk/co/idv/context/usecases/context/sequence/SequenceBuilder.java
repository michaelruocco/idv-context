package uk.co.idv.context.usecases.context.sequence;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.sequence.Sequence;
import uk.co.idv.context.entities.context.sequence.SequencesRequest;
import uk.co.idv.context.entities.policy.sequence.SequencePolicy;
import uk.co.idv.context.usecases.context.sequence.stage.StagesBuilder;

@RequiredArgsConstructor
public class SequenceBuilder {

    private final StagesBuilder stagesBuilder;

    public Sequence build(SequencesRequest request, SequencePolicy sequencePolicy) {
        return Sequence.builder()
                .name(sequencePolicy.getName())
                .stages(stagesBuilder.build(request.toStagesRequest(sequencePolicy)))
                .build();
    }

}
