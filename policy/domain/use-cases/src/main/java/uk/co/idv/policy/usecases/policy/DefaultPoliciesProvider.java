package uk.co.idv.policy.usecases.policy;

import lombok.RequiredArgsConstructor;
import uk.co.idv.policy.entities.policy.Policies;
import uk.co.idv.policy.entities.policy.Policy;
import uk.co.mruoc.file.content.ClasspathFileContentLoader;
import uk.co.mruoc.file.content.FileContentLoader;
import uk.co.mruoc.json.JsonConverter;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class DefaultPoliciesProvider<P extends Policy> {

    private final JsonConverter converter;
    private final Class<P> type;
    private final Collection<String> paths;
    private final FileContentLoader contentLoader;

    public DefaultPoliciesProvider(JsonConverter converter, Class<P> type, Collection<String> paths) {
        this(converter, type, paths, new ClasspathFileContentLoader());
    }

    public Policies<P> getPolicies() {
        return new Policies<>(loadPoliciesJson()
                .map(json -> converter.toObject(json, type))
                .collect(Collectors.toList())
        );
    }

    private Stream<String> loadPoliciesJson() {
        return paths.stream().map(contentLoader::loadContent);
    }

}
