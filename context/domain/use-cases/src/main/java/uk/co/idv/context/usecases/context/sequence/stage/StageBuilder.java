package uk.co.idv.context.usecases.context.sequence.stage;

import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.method.Methods;
import uk.co.idv.context.entities.context.sequence.stage.Stage;
import uk.co.idv.context.entities.context.sequence.stage.StagesRequest;
import uk.co.idv.context.entities.policy.sequence.stage.StagePolicy;
import uk.co.idv.context.usecases.context.method.MethodsBuilder;

@RequiredArgsConstructor
public class StageBuilder {

    private final MethodsBuilder methodsBuilder;

    public Stage build(StagesRequest request, StagePolicy stagePolicy) {
        Methods methods = methodsBuilder.build(request.toMethodsRequest(stagePolicy));
        return Stage.builder()
                .type(stagePolicy.getType())
                .methods(methods)
                .build();
    }

}
