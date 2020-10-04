package uk.co.idv.method.entities.result;

public interface ResultsMother {

    static Results build() {
        return with(ResultMother.build());
    }

    static Results with(Result result) {
        return new Results(result);
    }

}
