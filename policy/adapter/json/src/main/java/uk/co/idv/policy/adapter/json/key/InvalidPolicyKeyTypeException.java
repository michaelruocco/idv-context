package uk.co.idv.policy.adapter.json.key;

public class InvalidPolicyKeyTypeException extends RuntimeException {

    public InvalidPolicyKeyTypeException(String type) {
        super(type);
    }

}
