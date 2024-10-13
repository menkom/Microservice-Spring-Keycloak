package info.mastera.userapi.mapper;

import info.mastera.userapi.dto.KeycloakLoginResponse;
import info.mastera.userapi.dto.LoginResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class LoginResponseMapperTest {

    private final LoginResponseMapper loginResponseMapper = Mappers.getMapper(LoginResponseMapper.class);

    @Test
    void otEntityMappingTest() {
        var keycloakLoginResponse = new KeycloakLoginResponse(
                "accessToken",
                "refreshToken",
                5,
                7,
                "tokenType",
                "notBefore",
                "sessionState",
                "scope"
        );

        LoginResponse loginResponse = loginResponseMapper.toEntity(keycloakLoginResponse);

        Assertions.assertNotNull(loginResponse);
        Assertions.assertEquals("accessToken", loginResponse.accessToken());
        Assertions.assertEquals("refreshToken", loginResponse.refreshToken());
        Assertions.assertEquals(5, loginResponse.expiresIn());
        Assertions.assertEquals(7, loginResponse.refreshExpiresIn());
    }
}