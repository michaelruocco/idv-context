package uk.co.idv.context.entities.context.sequence;

import uk.co.idv.context.entities.context.method.MethodsMother;

public interface SequenceMother {

    static Sequence otpOnly() {
        return builder().build();
    }

    static Sequence.SequenceBuilder builder() {
        return Sequence.builder()
                .name("default-sequence")
                .methods(MethodsMother.otpOnly());
    }

}
