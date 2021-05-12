package uk.co.idv.identity.entities.channel.provideddata;

import uk.co.idv.identity.entities.channel.Channel;

public class DataAppenderFactory {

    public CompositeDataAppender build(Channel channel) {
        return new CompositeDataAppender(toAppenders(channel));
    }

    private static DataAppender[] toAppenders(Channel channel) {
        return new DataAppender[] {
                new ProvidedEmailAddressesAppender(channel),
                new ProvidedPhoneNumbersAppender(channel),
                new ProvidedMobileDevicesAppender(channel)
        };
    }

}
