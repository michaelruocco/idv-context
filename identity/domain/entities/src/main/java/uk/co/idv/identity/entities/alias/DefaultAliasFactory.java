package uk.co.idv.identity.entities.alias;

public class DefaultAliasFactory implements AliasFactory {

    @Override
    public Alias build(String type, String value) {
        switch(type) {
            case IdvId.TYPE: return new IdvId(value);
            case CardNumber.CREDIT_TYPE: return CardNumber.credit(value);
            case CardNumber.DEBIT_TYPE: return CardNumber.debit(value);
            default: throw new UnsupportedAliasTypeException(type);
        }
    }

}
