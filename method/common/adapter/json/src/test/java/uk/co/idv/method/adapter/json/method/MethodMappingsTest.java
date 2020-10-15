package uk.co.idv.method.adapter.json.method;

import com.fasterxml.jackson.databind.Module;
import org.junit.jupiter.api.Test;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.fake.FakeMethod;
import uk.co.idv.method.entities.method.fake.policy.FakeMethodPolicy;
import uk.co.idv.method.entities.policy.MethodPolicy;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class MethodMappingsTest {

    @Test
    void shouldReturnMethodTypeMap() {
        MethodMapping methodMapping = methodMappingBuilder()
                .methodType(Method.class)
                .build();
        MethodMapping fakeMethodMapping = fakeMethodMappingBuilder()
                .methodType(FakeMethod.class)
                .build();
        MethodMappings mappings = new MethodMappings(methodMapping, fakeMethodMapping);

        Map<String, Class<? extends Method>> methodTypes = mappings.toMethodTypeMap();

        assertThat(methodTypes)
                .containsEntry(methodMapping.getName(), methodMapping.getMethodType())
                .containsEntry(fakeMethodMapping.getName(), fakeMethodMapping.getMethodType())
                .hasSize(2);
    }

    @Test
    void shouldReturnPolicyTypeMap() {
        MethodMapping methodMapping = methodMappingBuilder()
                .policyType(MethodPolicy.class)
                .build();
        MethodMapping fakeMethodMapping = fakeMethodMappingBuilder()
                .policyType(FakeMethodPolicy.class)
                .build();
        MethodMappings mappings = new MethodMappings(methodMapping, fakeMethodMapping);

        Map<String, Class<? extends MethodPolicy>> policyTypes = mappings.toPolicyTypeMap();

        assertThat(policyTypes)
                .containsEntry(methodMapping.getName(), methodMapping.getPolicyType())
                .containsEntry(fakeMethodMapping.getName(), fakeMethodMapping.getPolicyType())
                .hasSize(2);
    }

    @Test
    void shouldReturnModules() {
        MethodMapping methodMapping = methodMappingBuilder()
                .modules(Arrays.asList(mock(Module.class), mock(Module.class)))
                .build();
        MethodMapping fakeMethodMapping = fakeMethodMappingBuilder()
                .modules(Arrays.asList(mock(Module.class), mock(Module.class)))
                .build();
        MethodMappings mappings = new MethodMappings(methodMapping, fakeMethodMapping);

        Collection<Module> modules = mappings.toModules();

        assertThat(modules)
                .containsAll(methodMapping.getModules())
                .containsAll(fakeMethodMapping.getModules())
                .hasSize(4);
    }

    private static DefaultMethodMapping.DefaultMethodMappingBuilder methodMappingBuilder() {
        return DefaultMethodMapping.builder().name("method-mapping");
    }

    private static DefaultMethodMapping.DefaultMethodMappingBuilder fakeMethodMappingBuilder() {
        return DefaultMethodMapping.builder().name("fake-method-mapping");
    }

}
