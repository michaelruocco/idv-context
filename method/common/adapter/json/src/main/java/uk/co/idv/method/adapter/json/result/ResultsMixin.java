package uk.co.idv.method.adapter.json.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.method.entities.result.Result;

import java.util.Collection;

public interface ResultsMixin {

    @JsonIgnore
    Collection<Result> getValues();

}
