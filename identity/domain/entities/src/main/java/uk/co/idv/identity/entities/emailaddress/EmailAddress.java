package uk.co.idv.identity.entities.emailaddress;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.With;
import uk.co.idv.identity.entities.protect.Updatable;

@Data
@RequiredArgsConstructor
public class EmailAddress implements Updatable<EmailAddress> {

    @With
    private final String value;

}
