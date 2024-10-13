package info.mastera.userapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakAccessRequest {
    private Boolean view;
    private Boolean mapRoles;
    private Boolean manage;
    private Boolean manageGroupMembership;
    private Boolean impersonate;
}
