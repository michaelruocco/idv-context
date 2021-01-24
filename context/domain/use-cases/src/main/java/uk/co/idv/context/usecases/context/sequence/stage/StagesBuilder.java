package uk.co.idv.context.usecases.context.sequence.stage;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.sequence.stage.Stages;
import uk.co.idv.context.entities.context.sequence.stage.StagesRequest;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class StagesBuilder {

    private final StageBuilder stageBuilder;

    public Stages build(StagesRequest request) {
        return new Stages(request.getPolicies().stream()
                .map(policy -> stageBuilder.build(request, policy))
                .collect(Collectors.toList())
        );
    }

}
