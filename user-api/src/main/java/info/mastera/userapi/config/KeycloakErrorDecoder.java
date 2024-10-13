package info.mastera.userapi.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import info.mastera.userapi.exception.KeycloakException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KeycloakErrorDecoder implements ErrorDecoder {

    private static final int SERVER_ERROR_CODE = 500;

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("Keycloak communication exception: {}", response);
        return switch (response.status()) {
            case 400 -> new KeycloakException(response.status(), "bad_request");
            case 401 -> new KeycloakException(response.status(), "invalid_credentials");
            case 403 -> new KeycloakException(response.status(), "forbidden");
            case 404 -> new KeycloakException(response.status(), "not_found");
            default -> new KeycloakException(SERVER_ERROR_CODE, "general_server_error");
        };
    }
}
