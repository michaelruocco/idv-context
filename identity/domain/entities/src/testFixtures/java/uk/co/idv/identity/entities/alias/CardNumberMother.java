package uk.co.idv.identity.entities.alias;


public interface CardNumberMother {

    static CardNumber credit() {
        return creditWithValue("4929111111111111");
    }

    static CardNumber creditWithValueEndingIn9() {
        return creditWithValue("4929111111111119");
    }

    static CardNumber creditWithValue(String value) {
        return CardNumber.credit(value);
    }

    static CardNumber debit() {
        return debitWithValue("4929222222222222");
    }

    static CardNumber debit1() { return debitWithValue("5929111111111111"); }

    static CardNumber debitWithValue(String value) {
        return CardNumber.debit(value);
    }

}
