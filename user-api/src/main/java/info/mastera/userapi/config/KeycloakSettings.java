package info.mastera.userapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "keycloak.login")
public record KeycloakSettings(
        String clientId,
        String secret,
        String grantTypePassword,
        String grantTypeRefresh
) {
}
