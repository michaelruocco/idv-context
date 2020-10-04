package uk.co.idv.context.adapter.json.context.result;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import uk.co.idv.method.entities.result.Result;
import uk.co.idv.method.entities.result.Results;

import java.util.Collections;

public class ResultModule extends SimpleModule {

    public ResultModule() {
        super("result-module", Version.unknownVersion());

        setMixInAnnotation(Results.class, ResultsMixin.class);

        addDeserializer(Result.class, new ResultDeserializer());
        addDeserializer(Results.class, new ResultsDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new JavaTimeModule());
    }

}
