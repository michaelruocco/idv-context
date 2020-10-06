package uk.co.idv.method.adapter.json.otp.delivery;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import uk.co.idv.common.adapter.json.ObjectMapperFactory;
import uk.co.idv.method.entities.otp.delivery.DeliveryMethod;
import uk.co.idv.method.entities.otp.delivery.phone.sms.SmsDeliveryMethodMother;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

class DeliveryMethodWithAsyncFutureSimSwapEligibilitySerdeTest {

    private static final ObjectMapper MAPPER = ObjectMapperFactory.build(new DeliveryMethodModule());
    private static final DeliveryMethod METHOD = SmsDeliveryMethodMother.smsWithAsyncSimSwapIneligible();
    private static final String JSON = SmsDeliveryMethodJsonMother.smsIneligible();

    @Test
    void shouldSerialize() throws JsonProcessingException {
        String json = MAPPER.writeValueAsString(METHOD);

        assertThatJson(json).isEqualTo(JSON);
    }

}
