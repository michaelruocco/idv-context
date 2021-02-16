package uk.co.idv.context.adapter.json.context.method;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.method.entities.method.Methods;
import uk.co.idv.method.adapter.json.method.MethodMapping;
import uk.co.idv.method.adapter.json.method.MethodMappings;
import uk.co.idv.method.adapter.json.eligibility.EligibilityModule;
import uk.co.idv.method.adapter.json.method.MethodMixin;
import uk.co.idv.method.entities.method.Method;

import java.util.ArrayList;
import java.util.Collection;

public class MethodModule extends SimpleModule {

    private final transient MethodMappings mappings;

    public MethodModule(MethodMapping... mappings) {
        this(new MethodMappings(mappings));
    }

    public MethodModule(MethodMappings mappings) {
        super("method-module", Version.unknownVersion());
        this.mappings = mappings;

        setMixInAnnotation(Method.class, MethodMixin.class);
        setMixInAnnotation(Methods.class, MethodsMixin.class);

        addDeserializer(Method.class, new MethodDeserializer(mappings));
        addDeserializer(Methods.class, new MethodsDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        Collection<Module> dependencies = new ArrayList<>(getMethodModules());
        dependencies.add(new EligibilityModule());
        return dependencies;
    }

    public Collection<Module> getMethodModules() {
        return mappings.toModules();
    }

}
