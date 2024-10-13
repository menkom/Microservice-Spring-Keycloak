package info.mastera.userapi.mapper;

import info.mastera.userapi.dto.KeycloakRoleRequest;
import info.mastera.userapi.dto.KeycloakRoleResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface KeycloakRoleResponseMapper {

    KeycloakRoleRequest toEntity(KeycloakRoleResponse request);
}
