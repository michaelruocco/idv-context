package uk.co.idv.identity.entities.identity;


public interface IdentitiesMother {

    static Identities two() {
        return with(
                IdentityMother.example(),
                IdentityMother.example1()
        );
    }

    static Identities empty() {
        return with();
    }

    static Identities with(Identity... identities) {
        return new Identities(identities);
    }

}
