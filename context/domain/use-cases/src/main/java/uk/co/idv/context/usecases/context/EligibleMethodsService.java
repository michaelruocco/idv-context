package uk.co.idv.context.usecases.context;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.EligibleMethodsContext;
import uk.co.idv.context.entities.context.EligibleMethodsContextRequest;

@RequiredArgsConstructor
public class EligibleMethodsService {

    private final ContextService contextService;

    public EligibleMethodsContext find(EligibleMethodsContextRequest request) {
        Context context = contextService.findWithEligibleIncompleteMethods(request.getContextId());
        return EligibleMethodsContext.builder()
                .id(context.getId())
                .activity(context.getActivity())
                .protectSensitiveData(context.isProtectSensitiveData())
                .methods(context.getNextMethods(request.getMethodName()))
                .build();
    }

}
