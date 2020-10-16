package uk.co.idv.policy.adapter.json.error;

import uk.co.idv.policy.adapter.json.error.policynotfound.PolicyNotFoundError;

import java.util.UUID;

public interface PolicyNotFoundErrorMother {

    static PolicyNotFoundError policyNotFoundError() {
        return policyNotFoundError(UUID.fromString("24a4c435-1ede-431a-979c-3075485bace6"));
    }

    static PolicyNotFoundError policyNotFoundError(UUID id) {
        return new PolicyNotFoundError(id.toString());
    }

}
