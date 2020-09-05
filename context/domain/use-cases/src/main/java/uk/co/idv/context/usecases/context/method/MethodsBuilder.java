package uk.co.idv.context.usecases.context.method;

import lombok.RequiredArgsConstructor;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.policy.method.MethodPolicies;
import uk.co.idv.identity.entities.identity.Identity;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MethodsBuilder {

    private final CompositeMethodBuilder methodBuilder;

    public Methods build(Identity identity, MethodPolicies methodPolicies) {
        return new Methods(methodPolicies.stream()
                .map(methodPolicy -> methodBuilder.build(identity, methodPolicy))
                .collect(Collectors.toList())
        );
    }

}
