package info.mastera.userapi.dto;

public record KeycloakCredentials(
        String type,
        String value,
        Boolean temporary
) {
}
