package uk.co.idv.context.entities.context.sequence;


public interface SequencesMother {

    static Sequences fakeOnly() {
        return new Sequences(SequenceMother.fakeOnly());
    }

    static Sequences empty() {
        return new Sequences();
    }

    static Sequences with(Sequence... sequences) {
        return new Sequences(sequences);
    }

}
