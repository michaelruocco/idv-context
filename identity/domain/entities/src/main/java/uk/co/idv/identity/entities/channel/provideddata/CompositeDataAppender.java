package uk.co.idv.identity.entities.channel.provideddata;

import lombok.RequiredArgsConstructor;
import uk.co.idv.identity.entities.identity.Identity;

import java.util.Arrays;
import java.util.Collection;

@RequiredArgsConstructor
public class CompositeDataAppender implements DataAppender {

    private final DataAppender composite;

    public CompositeDataAppender(DataAppender... appenders) {
        this(Arrays.asList(appenders));
    }

    public CompositeDataAppender(Collection<DataAppender> updates) {
        this(updates.stream().reduce(s -> s, (a, b) -> s -> b.apply(a.apply(s))));
    }

    @Override
    public Identity apply(Identity identity) {
        return composite.apply(identity);
    }

}
