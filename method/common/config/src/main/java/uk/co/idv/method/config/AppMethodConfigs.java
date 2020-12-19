package uk.co.idv.method.config;

import lombok.RequiredArgsConstructor;
import uk.co.idv.method.entities.method.Method;
import uk.co.idv.method.usecases.MethodBuilders;
import uk.co.idv.method.usecases.protect.CompositeMethodProtector;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AppMethodConfigs {

    private final Collection<AppMethodConfig> configs;

    public AppMethodConfigs(AppMethodConfig... configs) {
        this(Arrays.asList(configs));
    }

    public MethodBuilders methodBuilders() {
        return new MethodBuilders(configs.stream()
                .map(AppMethodConfig::methodBuilder)
                .collect(Collectors.toList())
        );
    }

    public UnaryOperator<Method> methodProtector() {
        return new CompositeMethodProtector(configs.stream()
                .map(AppMethodConfig::methodProtector)
                .collect(Collectors.toList())
        );
    }

}
