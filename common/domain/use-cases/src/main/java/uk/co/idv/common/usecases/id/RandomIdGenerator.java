package uk.co.idv.common.usecases.id;

import java.util.UUID;

public class RandomIdGenerator implements IdGenerator {

    @Override
    public UUID generate() {
        return UUID.randomUUID();
    }

}
