package uk.co.idv.method.usecases;

public interface FakeMethodBuilderMother {

    static FakeMethodBuilder build() {
        return new FakeMethodBuilder();
    }

}
