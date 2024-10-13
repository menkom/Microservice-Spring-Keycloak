package info.mastera.userapi.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public record KeycloakRoleResponse(
        String id,
        String name,
        String description,
        Boolean composite,
        Boolean clientRole,
        String containerId,
        Object attributes
) {
}
