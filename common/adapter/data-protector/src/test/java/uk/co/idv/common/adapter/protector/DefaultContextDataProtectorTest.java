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
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class DefaultContextDataProtectorTest {

    private final Method protectedMethod = mock(Method.class);
    private final DataProtector<PhoneNumber> phoneProtector = toFakeMasker(PhoneNumber.class, '*');
    private final DataProtector<EmailAddress> emailProtector = toFakeMasker(EmailAddress.class, '-');

    @Test
    void shouldReturnNonSensitiveFieldsUnchanged() {
        ContextDataProtector protector = buildProtector(phoneProtector, emailProtector);
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
        ContextDataProtector protector = buildProtector(phoneProtector, emailProtector);
        Context context = ContextMother.build();

        Context protectedContext = protector.apply(context);

        Channel channel = protectedContext.getChannel();
        assertThat(channel.getPhoneNumbers()).containsExactly();
    }

    @Test
    void shouldMaskChannelEmailAddresses() {
        ContextDataProtector protector = buildProtector(phoneProtector, emailProtector);
        Context context = ContextMother.build();

        Context protectedContext = protector.apply(context);

        Channel channel = protectedContext.getChannel();
        assertThat(channel.getPhoneNumbers()).containsExactly();
    }

    @Test
    void shouldMaskIdentityPhoneNumbers() {
        ContextDataProtector protector = buildProtector(phoneProtector, emailProtector);
        PhoneNumber phoneNumber = PhoneNumberMother.example();
        Context context = ContextMother.withIdentity(IdentityMother.withPhoneNumbers(phoneNumber));

        Context protectedContext = protector.apply(context);

        Identity identity = protectedContext.getIdentity();
        assertThat(identity.getPhoneNumbers()).containsExactly(phoneNumber.withValue("*************"));
    }

    @Test
    void shouldMaskIdentityEmailAddresses() {
        ContextDataProtector protector = buildProtector(phoneProtector, emailProtector);
        EmailAddress emailAddress = EmailAddressMother.bugsBunny();
        Context context = ContextMother.withIdentity(IdentityMother.withEmailAddresses(emailAddress));

        Context protectedContext = protector.apply(context);

        Identity identity = protectedContext.getIdentity();
        assertThat(identity.getEmailAddresses()).containsExactly(emailAddress.withValue("--------------------"));
    }

    @Test
    void shouldMaskMethods() {
        ContextDataProtector protector = buildProtector(phoneProtector, emailProtector);
        Sequence sequence = SequenceMother.fakeOnly();
        Context context = ContextMother.withSequences(SequencesMother.with(sequence));

        Context protectedContext = protector.apply(context);

        Sequences sequences = protectedContext.getSequences();
        assertThat(sequences).containsExactly(sequence.withMethods(new Methods(protectedMethod)));
    }

    @Test
    void shouldThrowExceptionIfNoSupportingDataProtectors() {
        DataProtector<?> dataProtector = mock(DataProtector.class);
        given(dataProtector.supports(any(Class.class))).willReturn(false);
        ContextDataProtector protector = buildProtector(dataProtector);
        Sequence sequence = SequenceMother.fakeOnly();
        Context context = ContextMother.withSequences(SequencesMother.with(sequence));

        Throwable error = catchThrowable(() -> protector.apply(context));

        assertThat(error)
                .isInstanceOf(ProtectionNotSupportedException.class)
                .hasMessage("uk.co.idv.identity.entities.emailaddress.EmailAddress");
    }

    private ContextDataProtector buildProtector(DataProtector<?>... dataProtectors) {
        return DefaultContextDataProtector.builder()
                .methodProtector(method -> protectedMethod)
                .dataProtectors(Arrays.asList(dataProtectors))
                .build();
    }

    private static <T> DataProtector<T> toFakeMasker(Class<T> type, char maskChar) {
        return new DataProtector<>(type, new FakeMasker(maskChar));
    }

}
