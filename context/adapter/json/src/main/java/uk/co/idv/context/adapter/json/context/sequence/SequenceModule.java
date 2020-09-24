package uk.co.idv.context.adapter.json.context.sequence;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.context.method.MethodModule;
import uk.co.idv.context.entities.context.sequence.Sequence;
import uk.co.idv.context.entities.context.sequence.Sequences;

import java.util.Collections;

public class SequenceModule extends SimpleModule {

    public SequenceModule() {
        super("sequence-module", Version.unknownVersion());

        setMixInAnnotation(Sequences.class, SequencesMixin.class);

        addDeserializer(Sequence.class, new SequenceDeserializer());
        addDeserializer(Sequences.class, new SequencesDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new MethodModule());
    }

}
