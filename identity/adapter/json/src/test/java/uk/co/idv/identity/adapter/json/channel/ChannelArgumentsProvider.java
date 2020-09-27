package uk.co.idv.identity.adapter.json.channel;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import uk.co.idv.identity.entities.channel.DefaultChannelMother;
import uk.co.idv.identity.entities.channel.de.DeRsaMother;
import uk.co.idv.identity.entities.channel.gb.AbcMother;
import uk.co.idv.identity.entities.channel.gb.GbRsaMother;

import java.util.stream.Stream;

public class ChannelArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(DefaultChannelJsonMother.build(), DefaultChannelMother.build()),
                Arguments.of(AbcJsonMother.abc(), AbcMother.abc()),
                Arguments.of(GbRsaJsonMother.gbRsa(), GbRsaMother.rsa()),
                Arguments.of(GbRsaJsonMother.gbRsaWithoutIssuerSessionId(), GbRsaMother.withoutIssuerSessionId()),
                Arguments.of(DeRsaJsonMother.deRsa(), DeRsaMother.rsa())
        );
    }

}
