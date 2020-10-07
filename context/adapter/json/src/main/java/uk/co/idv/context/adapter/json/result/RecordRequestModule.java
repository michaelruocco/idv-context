package uk.co.idv.context.adapter.json.result;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.context.ContextMixin;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.result.FacadeRecordResultRequest;
import uk.co.idv.method.adapter.json.result.ResultModule;

import java.util.Collections;

public class RecordRequestModule extends SimpleModule {

    public RecordRequestModule() {
        super("record-result-module", Version.unknownVersion());

        setMixInAnnotation(Context.class, ContextMixin.class);

        addDeserializer(FacadeRecordResultRequest.class, new FacadeRecordResultRequestDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new ResultModule());
    }

}
