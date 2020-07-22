package uk.co.idv.context.entities.policy.key;

import org.apache.commons.collections4.IterableUtils;

import java.util.Collection;

public class CollectionUtils {

    public static String getFirst(Collection<String> values) {
        return IterableUtils.get(values, 0);
    }

}
