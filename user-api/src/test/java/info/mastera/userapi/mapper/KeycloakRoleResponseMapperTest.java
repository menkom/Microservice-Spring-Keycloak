package info.mastera.userapi.mapper;

import info.mastera.userapi.dto.KeycloakRoleRequest;
import info.mastera.userapi.dto.KeycloakRoleResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class KeycloakRoleResponseMapperTest {

    private final KeycloakRoleResponseMapper roleResponseMapper = Mappers.getMapper(KeycloakRoleResponseMapper.class);


    @Test
    void otEntityMappingTest() {
        var keycloakRoleResponse = new KeycloakRoleResponse(
                "userId",
                "username",
                "description",
                true,
                false,
                "containerId",
                null
        );

        KeycloakRoleRequest keycloakRoleRequest = roleResponseMapper.toEntity(keycloakRoleResponse);

        Assertions.assertNotNull(keycloakRoleRequest);
        Assertions.assertEquals("userId", keycloakRoleRequest.id());
        Assertions.assertEquals("username", keycloakRoleRequest.name());
    }
}