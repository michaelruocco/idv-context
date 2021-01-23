package uk.co.idv.context.adapter.json.policy.sequence.nextmethods;

public class InvalidNextMethodPolicyTypeException extends RuntimeException {

    public InvalidNextMethodPolicyTypeException(String type) {
        super(type);
    }

}
