package uk.co.idv.identity.entities.alias;

public interface DefaultAliasMother {

    static DefaultAlias withType(String type) {
        return builder().type(type).build();
    }

    static DefaultAlias withValue(String value) {
        return builder().value(value).build();
    }

    static DefaultAlias build() {
        return builder().build();
    }

    static DefaultAlias.DefaultAliasBuilder builder() {
        return DefaultAlias.builder()
                .type("default-alias")
                .value("11111111");
    }

}
