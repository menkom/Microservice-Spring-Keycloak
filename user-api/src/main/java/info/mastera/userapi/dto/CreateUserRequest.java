package info.mastera.userapi.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank(message = "username_not_match")
        String username
) {
}
