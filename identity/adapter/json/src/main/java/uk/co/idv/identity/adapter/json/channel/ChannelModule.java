package uk.co.idv.identity.adapter.json.channel;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import uk.co.idv.identity.adapter.json.channel.de.DeRsaChannelDeserializer;
import uk.co.idv.identity.adapter.json.channel.defaultchannel.DefaultChannelDeserializer;
import uk.co.idv.identity.adapter.json.channel.gb.As3ChannelDeserializer;
import uk.co.idv.identity.adapter.json.channel.gb.GbRsaChannelDeserializer;
import uk.co.idv.identity.entities.channel.Channel;
import uk.co.idv.identity.entities.channel.DefaultChannel;
import uk.co.idv.identity.entities.channel.de.DeRsa;
import uk.co.idv.identity.entities.channel.gb.As3;
import uk.co.idv.identity.entities.channel.gb.GbRsa;

import java.util.Collections;

public class ChannelModule extends SimpleModule {

    public ChannelModule() {
        super("channel-module", Version.unknownVersion());
        setUpDefaults();
        setUpGbChannels();
        setUpDeChannels();
    }

    @Override
    public Iterable<? extends Module> getDependencies() {
        return Collections.singleton(new Jdk8Module());
    }

    private void setUpDefaults() {
        setMixInAnnotation(GbRsa.class, RsaMixin.class);
        setMixInAnnotation(DeRsa.class, RsaMixin.class);

        addDeserializer(Channel.class, new ChannelDeserializer());
        addDeserializer(DefaultChannel.class, new DefaultChannelDeserializer());
    }

    private void setUpGbChannels() {
        addDeserializer(GbRsa.class, new GbRsaChannelDeserializer());
        addDeserializer(As3.class, new As3ChannelDeserializer());
    }

    private void setUpDeChannels() {
        addDeserializer(DeRsa.class, new DeRsaChannelDeserializer());
    }

}
