package info.mastera.userapi.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public abstract class KeycloakAbstractUserInfo {
    @JsonAlias({"firstName", "given_name"})
    private String firstName;

    @JsonAlias({"lastName", "family_name"})
    private String lastName;

    @JsonAlias({"username", "preferred_username"})
    private String username;

    private String email;

    @JsonAlias({"emailVerified", "email_verified"})
    private Boolean emailVerified;
}
