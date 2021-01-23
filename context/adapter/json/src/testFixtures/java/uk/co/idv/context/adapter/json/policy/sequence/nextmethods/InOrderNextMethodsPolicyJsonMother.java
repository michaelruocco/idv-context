package uk.co.idv.context.adapter.json.policy.sequence.nextmethods;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface InOrderNextMethodsPolicyJsonMother {

    static String build() {
        return loadContentFromClasspath("policy/sequence/next-methods/in-order-next-methods-policy.json");
    }

}
