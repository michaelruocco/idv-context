package uk.co.idv.context.adapter.json.policy.sequence.stage;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import uk.co.idv.context.adapter.json.policy.method.MethodPolicyModule;
import uk.co.idv.context.adapter.json.policy.sequence.stage.type.StageTypeDeserializer;
import uk.co.idv.context.adapter.json.policy.sequence.stage.type.StageTypeMixin;
import uk.co.idv.context.entities.policy.sequence.stage.StagePolicies;
import uk.co.idv.context.entities.policy.sequence.stage.StagePolicy;
import uk.co.idv.context.entities.policy.sequence.stage.StageType;
import uk.co.idv.method.adapter.json.method.MethodMapping;
import uk.co.idv.method.adapter.json.method.MethodMappings;

import java.util.Collections;

public class StagePolicyModule extends SimpleModule {

    private final transient MethodMappings mappings;

    public StagePolicyModule(MethodMapping... mappings) {
        this(new MethodMappings(mappings));
    }

    public StagePolicyModule(MethodMappings mappings) {
        super("stage-policy-module", Version.unknownVersion());
        this.mappings = mappings;
        setUpPolicies();
        setUpPolicy();
        setUpType();
    }

    private void setUpPolicies() {
        setMixInAnnotation(StagePolicies.class, StagePoliciesMixin.class);
        addDeserializer(StagePolicies.class, new StagePoliciesDeserializer());
    }

    private void setUpPolicy() {
        setMixInAnnotation(StagePolicy.class, StagePolicyMixin.class);
        addDeserializer(StagePolicy.class, new StagePolicyDeserializer());
    }

    private void setUpType() {
        setMixInAnnotation(StageType.class, StageTypeMixin.class);
        addDeserializer(StageType.class, new StageTypeDeserializer());
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new MethodPolicyModule(mappings));
    }

}
