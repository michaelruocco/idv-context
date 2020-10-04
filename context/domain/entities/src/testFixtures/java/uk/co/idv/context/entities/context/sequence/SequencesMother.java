package uk.co.idv.context.entities.context.sequence;


public interface SequencesMother {

    static Sequences otpOnly() {
        return new Sequences(SequenceMother.otpOnly());
    }

    static Sequences empty() {
        return new Sequences();
    }

    static Sequences withSequences(Sequence... sequences) {
        return new Sequences(sequences);
    }

}
