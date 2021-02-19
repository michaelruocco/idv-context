
package uk.co.idv.method.adapter.json.method;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface InvalidMethodJsonMother {

    static String invalid() {
        return loadContentFromClasspath("method/invalid-method.json");
    }

}
