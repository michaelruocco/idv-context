package uk.co.idv.context.adapter.json.context.method;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.entities.context.method.DefaultMethods;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.method.adapter.json.MethodMapping;
import uk.co.idv.method.adapter.json.eligibility.EligibilityModule;
import uk.co.idv.method.adapter.json.method.MethodMixin;
import uk.co.idv.method.adapter.json.result.ResultModule;
import uk.co.idv.method.entities.method.DefaultMethodConfig;
import uk.co.idv.method.entities.method.Method;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class MethodModule extends SimpleModule {

    private final transient Collection<MethodMapping> mappings;

    public MethodModule(MethodMapping... mappings) {
        this(Arrays.asList(mappings));
    }

    public MethodModule(Collection<MethodMapping> mappings) {
        super("method-module", Version.unknownVersion());
        this.mappings = mappings;

        setMixInAnnotation(Method.class, MethodMixin.class);
        setMixInAnnotation(DefaultMethods.class, MethodsMixin.class);

        addDeserializer(Method.class, new MethodDeserializer(mappings));
        addDeserializer(Methods.class, new MethodsDeserializer());
        addDeserializer(DefaultMethodConfig.class, new DefaultMethodConfigDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        Collection<Module> dependencies = new ArrayList<>(getMethodModules());
        dependencies.add(new ResultModule());
        dependencies.add(new EligibilityModule());
        return dependencies;
    }

    public Collection<Module> getMethodModules() {
        return mappings.stream()
                .map(MethodMapping::getModules)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

}
