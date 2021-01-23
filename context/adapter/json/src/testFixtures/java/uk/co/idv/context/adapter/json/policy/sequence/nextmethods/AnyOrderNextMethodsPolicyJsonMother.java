package uk.co.idv.context.adapter.json.policy.sequence.nextmethods;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public interface AnyOrderNextMethodsPolicyJsonMother {

    static String build() {
        return loadContentFromClasspath("policy/sequence/next-methods/any-order-next-methods-policy.json");
    }

}
