package uk.co.idv.context.entities.context.create;

import uk.co.idv.context.entities.activity.DefaultActivityMother;
import uk.co.idv.identity.entities.alias.AliasesMother;
import uk.co.idv.identity.entities.channel.gb.GbRsaMother;

public interface FacadeCreateContextRequestMother {

    static FacadeCreateContextRequest build() {
        return builder().build();
    }

    static FacadeCreateContextRequest.FacadeCreateContextRequestBuilder builder() {
        return FacadeCreateContextRequest.builder()
                .channel(GbRsaMother.rsa())
                .activity(DefaultActivityMother.build())
                .aliases(AliasesMother.creditCardNumberOnly());
    }

}
