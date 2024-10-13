package info.mastera.userapi.dto;

import info.mastera.userapi.enums.RequiredActionsEnum;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class KeycloakUserRequest extends KeycloakAbstractUserInfo {
    private Boolean enabled;
    private KeycloakAccessRequest access;
    private List<KeycloakCredentials> credentials;
    private List<RequiredActionsEnum> requiredActions;
}