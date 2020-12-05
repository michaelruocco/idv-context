package uk.co.idv.identity.adapter.json.identity;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface IdentityJsonMother {

    static String example() {
        return loadContentFromClasspath("identity/identity.json");
    }

    static String withMaskedSensitiveData() {
        return loadContentFromClasspath("identity/identity-with-masked-sensitive-data.json");
    }

}
