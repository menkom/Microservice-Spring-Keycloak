package info.mastera.userapi.mapper;

import info.mastera.userapi.dto.KeycloakLoginResponse;
import info.mastera.userapi.dto.LoginResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoginResponseMapper {

    LoginResponse toEntity(KeycloakLoginResponse request);
}
