package uk.co.idv.context.entities.alias;

import java.util.Arrays;

public class AliasesMother {

    public static Aliases empty() {
        return with();
    }

    public static Aliases build() {
        return with(IdvIdMother.build(), CreditCardNumberMother.build());
    }

    public static Aliases with(Alias... aliases) {
        return new Aliases(Arrays.asList(aliases));
    }

}
