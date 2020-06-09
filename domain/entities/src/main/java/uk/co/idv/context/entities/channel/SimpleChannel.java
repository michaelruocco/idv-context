package uk.co.idv.context.entities.channel;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SimpleChannel implements Channel {

    private final String id;
    private final CountryCode country;

}
