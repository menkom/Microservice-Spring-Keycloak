package info.mastera.userapi.dto;

public record LoginResponse(
        String accessToken,
        String refreshToken,
        Integer expiresIn,
        Integer refreshExpiresIn
) {

}
