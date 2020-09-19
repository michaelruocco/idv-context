package uk.co.idv.context.adapter.json.policy.method;

public class InvalidMethodPolicyNameException extends RuntimeException {

    public InvalidMethodPolicyNameException(String name) {
        super(name);
    }

}
