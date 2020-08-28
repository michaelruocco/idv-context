package uk.co.idv.context.entities.policy;

public interface ContextPolicyMother {

    static ContextPolicy build() {
        return new ContextPolicy();
    }

}
