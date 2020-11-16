package uk.co.idv.identity.adapter.repository.type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AliasDocument {

    private String type;
    private String value;

}
