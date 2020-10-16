package uk.co.idv.policy.usecases.policy.load;

import java.util.UUID;

public interface PolicyNotFoundExceptionMother {

    static PolicyNotFoundException build() {
        return new PolicyNotFoundException(UUID.fromString("24a4c435-1ede-431a-979c-3075485bace6"));
    }

}
