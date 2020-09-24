
package uk.co.idv.context.adapter.json.context.method;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface InvalidMethodJsonMother {

    static String invalid() {
        return loadContentFromClasspath("context/method/invalid-method.json");
    }

}
