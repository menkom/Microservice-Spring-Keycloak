package info.mastera.userapi.exception;

import lombok.Getter;

@Getter
public class KeycloakException extends RuntimeException {

    private final int errorCode;
    private final String message;

    public KeycloakException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
