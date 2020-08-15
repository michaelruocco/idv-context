package uk.co.idv.context.usecases.common;

import java.util.UUID;

public class RandomIdGenerator implements IdGenerator {

    @Override
    public UUID generate() {
        return UUID.randomUUID();
    }

}
