package uk.co.idv.common.adapter.protector;

import org.junit.jupiter.api.Test;
import uk.co.idv.context.entities.context.Context;
import uk.co.idv.context.entities.context.ContextMother;
import uk.co.idv.context.entities.context.method.Methods;
import uk.co.idv.context.entities.context.sequence.Sequence;
import uk.co.idv.context.entities.context.sequence.SequenceMother;
import uk.co.idv.context.entities.context.sequence.Sequences;
import uk.co.idv.context.entities.context.sequence.SequencesMother;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.emailaddress.EmailAddress;
import uk.co.idv.identity.entities.emailaddress.EmailAddressMother;
import uk.co.idv.identity.entities.identity.Identity;
import uk.co.idv.identity.entities.identity.IdentityMother;
import uk.co.idv.identity.entities.phonenumber.PhoneNumber;
import uk.co.idv.identity.entities.phonenumber.PhoneNumberMother;
import uk.co.idv.method.entities.method.Method;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class DefaultContextDataProtectorTest {

    private final Method protectedMethod = mock(Method.class);
    private final DataProtector<PhoneNumber> phoneProtector = toFakeMasker(PhoneNumber.class,'*');
    private final DataProtector<EmailAddress> emailProtector = toFakeMasker(EmailAddress.class, '-');

    private final ContextDataProtector protector = DefaultContextDataProtector.builder()
            .methodProtector(method -> protectedMethod)
            .dataProtectors(Arrays.asList(phoneProtector, emailProtector))
            .build();

    @Test
    void shouldReturnNonSensitiveFieldsUnchanged() {
        Context context = ContextMother.build();

        Context protectedContext = protector.apply(context);

        assertThat(protectedContext)
                .usingRecursiveComparison()
                .ignoringFields(
                        "initial.channel.emailAddresses",
                        "initial.channel.phoneNumbers",
                        "request.identity.emailAddresses",
                        "request.identity.phoneNumbers",
                        "sequences")
                .isEqualTo(context);
    }

    @Test
    void shouldMaskChannelPhoneNumbers() {
        Context context = ContextMother.build();

        Context protectedContext = protector.apply(context);

        Channel channel = protectedContext.getChannel();
        assertThat(channel.getPhoneNumbers()).containsExactly();
    }

    @Test
    void shouldMaskChannelEmailAddresses() {
        Context context = ContextMother.build();

        Context protectedContext = protector.apply(context);

        Channel channel = protectedContext.getChannel();
        assertThat(channel.getPhoneNumbers()).containsExactly();
    }

    @Test
    void shouldMaskIdentityPhoneNumbers() {
        PhoneNumber phoneNumber = PhoneNumberMother.example();
        Context context = ContextMother.withIdentity(IdentityMother.withPhoneNumbers(phoneNumber));

        Context protectedContext = protector.apply(context);

        Identity identity = protectedContext.getIdentity();
        assertThat(identity.getPhoneNumbers()).containsExactly(phoneNumber.withValue("*************"));
    }

    @Test
    void shouldMaskIdentityEmailAddresses() {
        EmailAddress emailAddress = EmailAddressMother.bugsBunny();
        Context context = ContextMother.withIdentity(IdentityMother.withEmailAddresses(emailAddress));

        Context protectedContext = protector.apply(context);

        Identity identity = protectedContext.getIdentity();
        assertThat(identity.getEmailAddresses()).containsExactly(emailAddress.withValue("--------------------"));
    }

    @Test
    void shouldMaskMethods() {
        Sequence sequence = SequenceMother.fakeOnly();
        Context context = ContextMother.withSequences(SequencesMother.with(sequence));

        Context protectedContext = protector.apply(context);

        Sequences sequences = protectedContext.getSequences();
        assertThat(sequences).containsExactly(sequence.withMethods(new Methods(protectedMethod)));
    }

    private static <T> DataProtector<T> toFakeMasker(Class<T> type, char maskChar) {
        return new DataProtector<>(type, new FakeMasker(maskChar));
    }

}
