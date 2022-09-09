package org.example.codeit.app.rest.profile.dto;

//import com.example.app.common.utility.View;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.codeit.app.rest.View;
import org.example.codeit.domain.profile.Role;

import java.util.Set;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter

@JsonView(View.Privileged.class)
public class RoleDTO {
    @JsonProperty(value = "id", index = 0)
    private Long id;

    @JsonView(View.Default.class)
    @JsonProperty(value = "name", index = 1)
    private String name;

//    @JsonProperty(value = "description", index = 2)
//    private String description;

    @JsonProperty(value = "permissions", index = 3)
    private Set<PermissionDTO> permissions;

    public static RoleDTO fromDomainRole(Role role) {
        return new RoleDTO(
                role.getId(),
                role.getName(),
                role.getPermissions().stream().map(PermissionDTO::fromDomainPermission).collect(Collectors.toSet())
        );
    }

    public Role toDomainRole() {
        return new Role(
                this.getId(),
                this.getName(),
                this.getPermissions().stream().map(PermissionDTO::toDomainPermission).collect(Collectors.toSet())
        );
    }
}
