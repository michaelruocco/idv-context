package uk.co.idv.app.spring.identity;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.idv.app.plain.Application;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;

import java.util.UUID;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class IdentityControllerTest {

    private final Application application = mock(Application.class);

    private final IdentityController controller = new IdentityController(application);

    @Test
    void shouldUpsertIdentity() {
        Identity identity = IdentityMother.example();
        Identity expected = IdentityMother.example1();
        given(application.update(identity)).willReturn(expected);

        ResponseEntity<Identity> response = controller.upsertIdentity(identity);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(expected);
    }

    @Test
    void shouldReturnLocationForUpsertedIdentity() {
        Identity identity = IdentityMother.example();
        Identity expected = IdentityMother.example1();
        given(application.update(identity)).willReturn(expected);

        ResponseEntity<Identity> response = controller.upsertIdentity(identity);

        String expectedLocation = String.format("/v1/identities/%s", expected.getIdvIdValue());
        assertThat(response.getHeaders()).contains(
                entry("Location", singletonList(expectedLocation))
        );
    }

    @Test
    void shouldGetIdentityByAliasTypeAndValue() {
        String aliasType = "my-alias-type";
        String aliasValue = "my-alias-value";
        Identity expected = IdentityMother.example();
        given(application.findIdentity(aliasType, aliasValue)).willReturn(expected);

        Identity identity = controller.getIdentity(aliasType, aliasValue);

        assertThat(identity).isEqualTo(expected);
    }

    @Test
    void shouldGetIdentityByIdvId() {
        UUID idvId = UUID.randomUUID();
        Identity expected = IdentityMother.example();
        given(application.findIdentity(idvId)).willReturn(expected);

        Identity identity = controller.getIdentity(idvId);

        assertThat(identity).isEqualTo(expected);
    }

}
