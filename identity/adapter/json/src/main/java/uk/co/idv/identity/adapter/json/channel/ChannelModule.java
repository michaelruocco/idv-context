package uk.co.idv.identity.adapter.json.channel;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import uk.co.idv.identity.adapter.json.channel.de.DeRsaChannelDeserializer;
import uk.co.idv.identity.adapter.json.channel.defaultchannel.DefaultChannelDeserializer;
import uk.co.idv.identity.adapter.json.channel.gb.AbcChannelDeserializer;
import uk.co.idv.identity.adapter.json.channel.gb.GbRsaChannelDeserializer;
import uk.co.idv.identity.adapter.json.emailaddress.EmailAddressModule;
import uk.co.idv.identity.adapter.json.phonenumber.PhoneNumberModule;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.channel.DefaultChannel;
import uk.co.idv.identity.entities.channel.de.DeRsa;
import uk.co.idv.identity.entities.channel.gb.Abc;
import uk.co.idv.identity.entities.channel.gb.GbRsa;

import java.util.Arrays;

public class ChannelModule extends SimpleModule {

    public ChannelModule() {
        super("channel-module", Version.unknownVersion());
        setUpDefaults();
        setUpGbChannels();
        setUpDeChannels();
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Arrays.asList(
                new Jdk8Module(),
                new EmailAddressModule(),
                new PhoneNumberModule()
        );
    }

    private void setUpDefaults() {
        setMixInAnnotation(Channel.class, ChannelMixin.class);

        addDeserializer(Channel.class, new ChannelDeserializer());
        addDeserializer(DefaultChannel.class, new DefaultChannelDeserializer());
    }

    private void setUpGbChannels() {
        setMixInAnnotation(GbRsa.class, RsaMixin.class);

        addDeserializer(GbRsa.class, new GbRsaChannelDeserializer());
        addDeserializer(Abc.class, new AbcChannelDeserializer());
    }

    private void setUpDeChannels() {
        setMixInAnnotation(DeRsa.class, RsaMixin.class);

        addDeserializer(DeRsa.class, new DeRsaChannelDeserializer());
    }

}
