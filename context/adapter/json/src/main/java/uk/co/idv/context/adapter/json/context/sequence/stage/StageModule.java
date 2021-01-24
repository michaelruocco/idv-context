package uk.co.idv.context.adapter.json.context.sequence.stage;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.policy.sequence.stage.StagePolicyModule;
import uk.co.idv.context.entities.context.sequence.stage.Stage;
import uk.co.idv.context.entities.context.sequence.stage.Stages;
import uk.co.idv.method.adapter.json.method.MethodMapping;
import uk.co.idv.method.adapter.json.method.MethodMappings;

import java.util.Collections;

public class StageModule extends SimpleModule {

    private final transient MethodMappings mappings;

    public StageModule(MethodMapping... mappings) {
        this(new MethodMappings(mappings));
    }

    public StageModule(MethodMappings mappings) {
        super("stage-module", Version.unknownVersion());
        this.mappings = mappings;
        setUpStages();
        setUpStage();
    }

    private void setUpStages() {
        setMixInAnnotation(Stages.class, StagesMixin.class);
        addDeserializer(Stages.class, new StagesDeserializer());
    }

    private void setUpStage() {
        setMixInAnnotation(Stage.class, StageMixin.class);
        addDeserializer(Stage.class, new StageDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new StagePolicyModule(mappings));
    }

}
