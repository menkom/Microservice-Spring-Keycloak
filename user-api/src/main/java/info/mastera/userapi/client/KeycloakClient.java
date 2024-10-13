package info.mastera.userapi.client;

import feign.Headers;
import feign.Response;
import info.mastera.userapi.config.KeycloakFeignConfig;
import info.mastera.userapi.dto.KeycloakLoginResponse;
import info.mastera.userapi.dto.KeycloakRoleRequest;
import info.mastera.userapi.dto.KeycloakRoleResponse;
import info.mastera.userapi.dto.KeycloakUserRequest;
import info.mastera.userapi.dto.KeycloakUserSearch;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "keycloak", url = "${keycloak.host}", configuration = {KeycloakFeignConfig.class})
public interface KeycloakClient {

    @PostMapping(value = "/realms/${keycloak.realm-name}/protocol/openid-connect/token", produces = "application/json;charset=UTF-8")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=UTF-8")
    KeycloakLoginResponse login(@RequestBody MultiValueMap<String, String> paramMap);

    @PostMapping(value = "/realms/${keycloak.realm-name}/protocol/openid-connect/logout")
    @Headers("Content-Type: application/x-www-form-urlencoded")
    void logout(@RequestBody MultiValueMap<String, String> paramMap);

    @GetMapping(value = "/admin/realms/${keycloak.realm-name}/users")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    List<KeycloakUserSearch> filterUser(
            @RequestHeader("Authorization") String accessToken,
            @RequestParam("username") String username
    );

    @PostMapping(value = "/admin/realms/${keycloak.realm-name}/users")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    Response createUser(
            @RequestHeader("Authorization") String accessToken,
            @RequestBody KeycloakUserRequest createUserKeycloakReq);

    @GetMapping(value = "/admin/realms/${keycloak.realm-name}/roles/{role-name}")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    KeycloakRoleResponse getRealmRole(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable("role-name") String roleName
    );

    @GetMapping(value = "/admin/realms/${keycloak.realm-name}/roles")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    List<KeycloakRoleResponse> getRealmRoles(@RequestHeader("Authorization") String accessToken);

    @PostMapping(value = "/admin/realms/${keycloak.realm-name}/users/{user-id}/role-mappings/realm")
    @Headers({"Content-Type:application/json", "Accept:application/json"})
    void assignRealmRole(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable("user-id") String userId,
            @RequestBody List<KeycloakRoleRequest> realmRoles
    );
}
