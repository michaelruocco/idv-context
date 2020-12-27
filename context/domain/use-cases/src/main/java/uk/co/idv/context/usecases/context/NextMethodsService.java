package uk.co.idv.context.usecases.context;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.NextMethods;
import uk.co.idv.context.entities.context.NextMethodsRequest;

@RequiredArgsConstructor
public class NextMethodsService {

    private final ContextService contextService;

    public NextMethods find(NextMethodsRequest request) {
        Context context = contextService.findWithEligibleIncompleteSequences(request.getContextId());
        return NextMethods.builder()
                .id(context.getId())
                .activity(context.getActivity())
                .protectSensitiveData(context.isProtectSensitiveData())
                .methods(context.getNextMethods(request.getMethodName()))
                .build();
    }

}
