package uk.co.idv.context.entities.context.result;

public interface ResultsMother {

    static Results build() {
        return with(ResultMother.build());
    }

    static Results with(Result result) {
        return new Results(result);
    }

}
