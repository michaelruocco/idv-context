package uk.co.idv.context.adapter.json.context.create;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import uk.co.idv.context.adapter.json.activity.ActivityModule;
import uk.co.idv.context.adapter.json.policy.ContextPolicyModule;
import uk.co.idv.context.entities.context.create.CreateContextRequest;
import uk.co.idv.context.entities.context.create.DefaultCreateContextRequest;
import uk.co.idv.context.entities.context.create.FacadeCreateContextRequest;
import uk.co.idv.identity.adapter.json.alias.AliasModule;
import uk.co.idv.identity.adapter.json.channel.ChannelModule;
import uk.co.idv.identity.adapter.json.identity.IdentityModule;

import java.util.Arrays;

public class CreateContextModule extends SimpleModule {

    public CreateContextModule() {
        super("create-context-module", Version.unknownVersion());

        setMixInAnnotation(CreateContextRequest.class, CreateContextRequestMixin.class);
        setMixInAnnotation(DefaultCreateContextRequest.class, DefaultCreateContextRequestMixin.class);

        addDeserializer(FacadeCreateContextRequest.class, new FacadeCreateContextRequestDeserializer());
        addDeserializer(DefaultCreateContextRequest.class, new DefaultCreateContextRequestDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new ChannelModule(),
                new ActivityModule(),
                new AliasModule(),
                new Jdk8Module(),
                new ContextPolicyModule(),
                new IdentityModule()
        );
    }

}
