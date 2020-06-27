package uk.co.idv.context.entities.identity;

import java.util.Arrays;

public interface IdentitiesMother {

    static Identities two() {
        return of(
                IdentityMother.example(),
                IdentityMother.example1()
        );
    }

    static Identities empty() {
        return of();
    }

    static Identities of(Identity... identities) {
        return new Identities(Arrays.asList(identities));
    }

}
