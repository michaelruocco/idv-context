package uk.co.idv.context.adapter.json.policy.key;

public class InvalidPolicyKeyTypeException extends RuntimeException {

    public InvalidPolicyKeyTypeException(String type) {
        super(type);
    }

}
