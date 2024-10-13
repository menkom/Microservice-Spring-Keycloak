package info.mastera.userapi.service;

import feign.Response;
import info.mastera.userapi.client.KeycloakClient;
import info.mastera.userapi.dto.KeycloakCredentials;
import info.mastera.userapi.dto.KeycloakUserRequest;
import info.mastera.userapi.mapper.KeycloakRoleResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private static final String ROLE_USER = "ROLE_USER";
    public static final String HEADER_NAME_LOCATION = "location";

    private final KeycloakClient keycloakClient;
    private final KeycloakRoleResponseMapper keycloakRoleResponseMapper;

    public void create(String accessToken, String username) {
        var isUserExist = keycloakClient.filterUser(accessToken, username)
                .stream()
                .anyMatch(user -> user.username().equals(username));
        if (isUserExist) {
            throw new IllegalArgumentException("Username already exists");
        }

        KeycloakUserRequest keycloakUserRequest = (KeycloakUserRequest) new KeycloakUserRequest()
                .setCredentials(
                        List.of(new KeycloakCredentials(null, null, false))
                )
                .setEnabled(true)
                .setUsername(username);
        try (var response = keycloakClient.createUser(accessToken, keycloakUserRequest)) {
            String userId = extractUserId(response);

            var realmRoleUser = keycloakClient.getRealmRole(accessToken, ROLE_USER);
            if (realmRoleUser == null) {
                throw new IllegalArgumentException("Security server error. No user role found");
            }

            var keycloakRoleRequest = keycloakRoleResponseMapper.toEntity(realmRoleUser);
            keycloakClient.assignRealmRole(accessToken, userId, List.of(keycloakRoleRequest));
        }
    }

    private String extractUserId(Response response) {
        if (response.status() != HttpStatus.CREATED.value()
                || response.headers().isEmpty()
                || !response.headers().containsKey(HEADER_NAME_LOCATION)
                || response.headers().get(HEADER_NAME_LOCATION).isEmpty()) {
            throw new IllegalArgumentException("Unexpected response on user creation: " + response);
        }

        return Optional.ofNullable(response.headers())
                .map(headers -> headers.get(HEADER_NAME_LOCATION))
                .map(locations -> locations.stream().findFirst().orElseThrow())
                .map(location -> location.split("/"))
                .map(l -> l[l.length - 1])
                .orElseThrow(() -> new IllegalArgumentException("Unexpected response on user creation: " + response));
    }
}
