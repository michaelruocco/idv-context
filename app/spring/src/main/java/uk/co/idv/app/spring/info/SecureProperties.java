package uk.co.idv.app.spring.info;

import java.util.Collection;
import java.util.Collections;

public class SecureProperties extends ReplaceProperties {

    public SecureProperties() {
        this(Collections.singleton("spring.data.mongodb.uri"));
    }

    public SecureProperties(Collection<String> propertyNames) {
        super(propertyNames, "hidden");
    }

}
