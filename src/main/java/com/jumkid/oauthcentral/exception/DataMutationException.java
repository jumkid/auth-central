package com.jumkid.oauthcentral.exception;

public class DataMutationException extends RuntimeException {

    public DataMutationException(String entity) {
        super(String.format("Failed to mutate entity %s.", entity));
    }

}
