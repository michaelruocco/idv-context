package uk.co.idv.method.entities.result;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface ResultsMother {

    static Results build() {
        return with(ResultMother.build());
    }

    static Results empty() {
        return new Results();
    }

    static Results with(Result result) {
        return new Results(result);
    }

    static Results withUnsuccessfulAttempts(int numberOfAttempts) {
        return new Results(IntStream.rangeClosed(1, numberOfAttempts)
                .mapToObj(i -> ResultMother.unsuccessful())
                .collect(Collectors.toList()));
    }

}
