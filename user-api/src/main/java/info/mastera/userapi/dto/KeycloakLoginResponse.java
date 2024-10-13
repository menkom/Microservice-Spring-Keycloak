package info.mastera.userapi.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KeycloakLoginResponse(
        String accessToken,
        String refreshToken,
        Integer expiresIn,
        Integer refreshExpiresIn,
        String tokenType,
        String notBeforePolicy,
        String sessionState,
        String scope
) {
}
