package uk.co.idv.context.entities.context;

import uk.co.idv.context.entities.activity.DefaultActivityMother;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.method.entities.method.fake.FakeMethodMother;

import java.util.UUID;

public interface NextMethodsMother {

    static NextMethods build() {
        return builder().build();
    }

    static NextMethods.NextMethodsBuilder builder() {
        return NextMethods.builder()
                .id(UUID.fromString("2948aadc-7f63-4b00-875b-77a4e6608e5c"))
                .protectSensitiveData(true)
                .activity(DefaultActivityMother.build())
                .methods(new Methods(FakeMethodMother.build()));
    }

}
