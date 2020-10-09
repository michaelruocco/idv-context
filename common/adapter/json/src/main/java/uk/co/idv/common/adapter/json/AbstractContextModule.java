package uk.co.idv.common.adapter.json;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Arrays;

public abstract class AbstractContextModule extends SimpleModule {

    protected AbstractContextModule(String name, Version version) {
        super(name, version);
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new Jdk8Module(),
                new JavaTimeModule()
        );
    }

}
