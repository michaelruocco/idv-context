package uk.co.idv.context.adapter.stub.identity.find;

import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;

public interface StubDataLoadPolicy {

    static boolean shouldLoad(Aliases aliases) {
        return aliases.stream().noneMatch(StubDataLoadPolicy::shouldLoad);
    }

    static boolean shouldLoad(Alias alias) {
        return alias.getValue().endsWith("9");
    }

}
