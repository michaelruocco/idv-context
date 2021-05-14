package uk.co.idv.identity.adapter.repository.converter;


import java.util.Collection;
import java.util.Collections;

public class NullCollectionToEmptyList {

    private NullCollectionToEmptyList() {
        // utility class
    }

    public static <T> Collection<T> toEmptyListIfNull(Collection<T> documents) {
        if (documents == null) {
            return Collections.emptyList();
        }
        return documents;
    }
}
