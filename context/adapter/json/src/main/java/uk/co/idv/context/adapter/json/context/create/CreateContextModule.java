package uk.co.idv.context.adapter.json.context.create;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import uk.co.idv.activity.adapter.json.ActivityModule;
import uk.co.idv.context.adapter.json.policy.ContextPolicyModule;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.ServiceCreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequest;
import uk.co.idv.identity.adapter.json.alias.AliasModule;
import uk.co.idv.identity.adapter.json.channel.ChannelModule;
import uk.co.idv.identity.adapter.json.identity.IdentityModule;
import uk.co.idv.method.adapter.json.method.MethodMapping;
import uk.co.idv.method.adapter.json.method.MethodMappings;

import java.util.Arrays;

public class CreateContextModule extends SimpleModule {

    private final transient MethodMappings mappings;

    public CreateContextModule(MethodMapping... mappings) {
        this(new MethodMappings(mappings));
    }

    public CreateContextModule(MethodMappings mappings) {
        super("create-context-module", Version.unknownVersion());
        this.mappings = mappings;

        setMixInAnnotation(CreateContextRequest.class, CreateContextRequestMixin.class);
        setMixInAnnotation(ServiceCreateContextRequest.class, ServiceCreateContextRequestMixin.class);

        addDeserializer(FacadeCreateContextRequest.class, new FacadeCreateContextRequestDeserializer());
        addDeserializer(ServiceCreateContextRequest.class, new DefaultCreateContextRequestDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new ChannelModule(),
                new ActivityModule(),
                new AliasModule(),
                new Jdk8Module(),
                new ContextPolicyModule(mappings),
                new IdentityModule()
        );
    }

}
