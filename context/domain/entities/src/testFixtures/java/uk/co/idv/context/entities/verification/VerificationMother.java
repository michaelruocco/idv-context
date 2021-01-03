package uk.co.idv.context.entities.verification;

import uk.co.idv.context.entities.activity.DefaultActivityMother;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;

import java.time.Instant;
import java.util.UUID;

public interface VerificationMother {

    static Verification incomplete() {
        return builder().build();
    }

    static Verification successful() {
        return successful(Instant.parse("2020-09-14T20:04:03.003Z"));
    }

    static Verification successful(Instant completed) {
        return builder()
                .successful(true)
                .completed(completed)
                .build();
    }

    static Verification.VerificationBuilder builder() {
        Method method = FakeMethodMother.build();
        return Verification.builder()
                .id(UUID.fromString("81e11840-140e-45ac-a6af-40aa653a146e"))
                .contextId(UUID.fromString("2948aadc-7f63-4b00-875b-77a4e6608e5c"))
                .activity(DefaultActivityMother.build())
                .methodName(method.getName())
                .methods(new Methods(method))
                .protectSensitiveData(true)
                .created(Instant.parse("2020-09-14T20:03:03.003Z"))
                .expiry(Instant.parse("2020-09-14T20:08:03.003Z"));
    }

}
