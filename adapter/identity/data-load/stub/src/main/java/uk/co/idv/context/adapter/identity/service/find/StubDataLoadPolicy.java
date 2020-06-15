package uk.co.idv.context.adapter.identity.service.find;

import uk.co.idv.context.entities.alias.Alias;
import uk.co.idv.context.entities.alias.Aliases;

public class StubDataLoadPolicy {

    private StubDataLoadPolicy() {
        // utility class
    }

    public static boolean shouldLoad(Aliases aliases) {
        return aliases.stream().noneMatch(StubDataLoadPolicy::shouldLoad);
    }

    public static boolean shouldLoad(Alias alias) {
        return alias.getValue().endsWith("9");
    }

}
