package info.mastera.userapi.service;

import info.mastera.userapi.client.KeycloakClient;
import info.mastera.userapi.config.KeycloakSettings;
import info.mastera.userapi.dto.LoginRequest;
import info.mastera.userapi.dto.LoginResponse;
import info.mastera.userapi.mapper.LoginResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    public static final String PARAM_CLIENT_ID = "client_id";
    public static final String PARAM_CLIENT_SECRET = "client_secret";
    public static final String PARAM_REFRESH_TOKEN = "refresh_token";
    public static final String PARAM_GRANT_TYPE = "grant_type";
    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_PASSWORD = "password";

    private final KeycloakClient keycloakClient;
    private final KeycloakSettings keycloakSettings;
    private final LoginResponseMapper loginResponseMapper;

    public LoginResponse login(LoginRequest login) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(PARAM_USERNAME, login.username());
        params.add(PARAM_PASSWORD, login.password());
        params.add(PARAM_GRANT_TYPE, keycloakSettings.grantTypePassword());
        params.add(PARAM_CLIENT_ID, keycloakSettings.clientId());
        params.add(PARAM_CLIENT_SECRET, keycloakSettings.secret());
        var loginResp = keycloakClient.login(params);
        return loginResponseMapper.toEntity(loginResp);
    }

    public LoginResponse refresh(String refreshToken) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(PARAM_GRANT_TYPE, keycloakSettings.grantTypeRefresh());
        params.add(PARAM_REFRESH_TOKEN, refreshToken);
        params.add(PARAM_CLIENT_ID, keycloakSettings.clientId());
        params.add(PARAM_CLIENT_SECRET, keycloakSettings.secret());
        var loginResp = keycloakClient.login(params);
        return loginResponseMapper.toEntity(loginResp);
    }

    public void logout(String refreshToken) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(PARAM_CLIENT_ID, keycloakSettings.clientId());
        params.add(PARAM_CLIENT_SECRET, keycloakSettings.secret());
        params.add(PARAM_REFRESH_TOKEN, refreshToken);
        keycloakClient.logout(params);
    }
}
