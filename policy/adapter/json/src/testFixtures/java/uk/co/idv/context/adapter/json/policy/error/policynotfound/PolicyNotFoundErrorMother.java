package uk.co.idv.context.adapter.json.policy.error.policynotfound;

import uk.co.idv.context.adapter.json.error.policynotfound.PolicyNotFoundError;

import java.util.UUID;

public interface PolicyNotFoundErrorMother {

    static PolicyNotFoundError policyNotFoundError() {
        return policyNotFoundError(UUID.fromString("24a4c435-1ede-431a-979c-3075485bace6"));
    }

    static PolicyNotFoundError policyNotFoundError(UUID id) {
        return new PolicyNotFoundError(id.toString());
    }

}
