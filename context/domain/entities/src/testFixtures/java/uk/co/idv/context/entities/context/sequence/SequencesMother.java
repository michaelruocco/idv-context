package uk.co.idv.context.entities.context.sequence;


public interface SequencesMother {

    static Sequences otpOnly() {
        return new Sequences(SequenceMother.otpOnly());
    }

}
