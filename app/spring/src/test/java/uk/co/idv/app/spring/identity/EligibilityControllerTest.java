package uk.co.idv.app.spring.identity;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.co.idv.app.manual.Application;
import uk.co.idv.identity.entities.eligibility.IdentityEligibility;
import uk.co.idv.identity.entities.eligibility.CreateEligibilityRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class EligibilityControllerTest {

    private final Application application = mock(Application.class);

    private final EligibilityController controller = new EligibilityController(application);

    @Test
    void shouldCreateEligibility() {
        CreateEligibilityRequest request = mock(CreateEligibilityRequest.class);
        IdentityEligibility expected = mock(IdentityEligibility.class);
        given(application.create(request)).willReturn(expected);

        ResponseEntity<IdentityEligibility> response = controller.createEligibility(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo(expected);
    }

}
