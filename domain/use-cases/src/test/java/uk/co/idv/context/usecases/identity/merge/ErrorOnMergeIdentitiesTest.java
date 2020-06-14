package uk.co.idv.context.usecases.identity.merge;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.identity.Identity;
import uk.co.idv.context.entities.identity.IdentityMother;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;

class ErrorOnMergeIdentitiesTest {

    private final MergeIdentities merge = new ErrorOnMergeIdentities();

    @Test
    void shouldThrowExceptionOnMerge() {
        Identity identity = IdentityMother.withoutIdvId();
        Collection<Identity> existingIdentities = Arrays.asList(
                IdentityMother.example(),
                IdentityMother.example1()
        );

        MultipleIdentitiesFoundException error = catchThrowableOfType(
                () -> merge.merge(identity, existingIdentities),
                MultipleIdentitiesFoundException.class
        );

        assertThat(error.getIdentity()).isEqualTo(identity);
        assertThat(error.getExistingIdentities()).isEqualTo(existingIdentities);
    }

}
