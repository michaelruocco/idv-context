package uk.co.idv.context.entities.channel;

import com.neovisionaries.i18n.CountryCode;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DefaultChannel implements Channel {

    private final String id;
    private final CountryCode country;

}
