package uk.co.idv.method.adapter.json.method;

import com.fasterxml.jackson.databind.Module;
import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.policy.MethodPolicy;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MethodMappings {

    private final Collection<MethodMapping> mappings;

    public MethodMappings(MethodMapping... mappings) {
        this(Arrays.asList(mappings));
    }

    public Map<String, Class<? extends Method>> toMethodTypeMap() {
        return mappings.stream().collect(Collectors.toMap(
                MethodMapping::getName,
                MethodMapping::getMethodType
        ));
    }

    public Map<String, Class<? extends MethodPolicy>> toPolicyTypeMap() {
        return mappings.stream().collect(Collectors.toMap(
                MethodMapping::getName,
                MethodMapping::getPolicyType
        ));
    }

    public Collection<Module> toModules() {
        return mappings.stream()
                .map(MethodMapping::getModules)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}
