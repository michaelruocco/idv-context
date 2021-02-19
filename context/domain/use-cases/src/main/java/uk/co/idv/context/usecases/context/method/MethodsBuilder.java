package uk.co.idv.context.usecases.context.method;

import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.method.DefaultMethods;
import uk.co.idv.method.entities.method.Methods;
import uk.co.idv.method.entities.method.MethodsRequest;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MethodsBuilder {

    private final CompositeMethodBuilder methodBuilder;

    public Methods build(MethodsRequest request) {
        return new DefaultMethods(request.getPolicies().stream()
                .map(methodPolicy -> methodBuilder.build(request, methodPolicy))
                .collect(Collectors.toList())
        );
    }

}
