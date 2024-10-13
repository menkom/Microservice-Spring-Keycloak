package info.mastera.userapi.controller;

import info.mastera.userapi.dto.CreateUserRequest;
import info.mastera.userapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(
            @RequestHeader("Authorization") String accessToken,
            @Valid @RequestBody CreateUserRequest createUserRequest
    ) {
        userService.create(accessToken, createUserRequest.username());
    }
}
