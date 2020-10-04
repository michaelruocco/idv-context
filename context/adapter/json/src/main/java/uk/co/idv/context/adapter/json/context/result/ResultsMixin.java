package uk.co.idv.context.adapter.json.context.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uk.co.idv.method.entities.result.Result;

import java.util.Collection;

public interface ResultsMixin {

    @JsonIgnore
    Collection<Result> getValues();

}
