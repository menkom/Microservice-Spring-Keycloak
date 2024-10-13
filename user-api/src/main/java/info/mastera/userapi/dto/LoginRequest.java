package info.mastera.userapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotBlank(message = "username_not_match")
        String username,

        @NotNull(message = "password_not_match")
        String password
) {
}
