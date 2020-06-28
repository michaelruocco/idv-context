package uk.co.idv.context.adapter.json.channel;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.context.entities.channel.Channel;
import uk.co.idv.context.entities.channel.DefaultChannelMother;
import uk.co.idv.context.entities.channel.de.DeRsaMother;
import uk.co.idv.context.entities.channel.gb.GbAs3Mother;
import uk.co.idv.context.entities.channel.gb.GbRsaMother;

import java.util.stream.Stream;

import static uk.co.mruoc.file.content.ContentLoader.loadContentFromClasspath;

public class ChannelArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                args("channel/default-channel.json", DefaultChannelMother.build()),
                args("channel/gb/as3.json", GbAs3Mother.as3()),
                args("channel/gb/gb-rsa.json", GbRsaMother.rsa()),
                args("channel/gb/gb-rsa-without-issuer-session-id.json", GbRsaMother.withoutIssuerSessionId()),
                args("channel/de/de-rsa.json", DeRsaMother.rsa())
        );
    }

    private static Arguments args(String path, Channel channel) {
        return Arguments.of(loadContentFromClasspath(path), channel);
    }

}
