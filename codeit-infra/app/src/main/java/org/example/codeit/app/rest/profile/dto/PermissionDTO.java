package org.example.codeit.app.rest.profile.dto;

//import com.example.app.common.utility.View;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.codeit.app.rest.View;
import org.example.codeit.domain.profile.Permission;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter

@JsonView(View.Privileged.class)
public class PermissionDTO {
    @JsonProperty(value = "id", index = 0)
    private Long id;
    @JsonView(View.Default.class)
    @JsonProperty(value = "name", index = 1)
    private String name;
    @JsonProperty(value = "description", index = 2)
    private String description;

    public static PermissionDTO fromDomainPermission(Permission permission) {
        return new PermissionDTO(
                permission.getId(),
                permission.getName(),
                permission.getDescription()
        );
    }

    public Permission toDomainPermission() {
        return new Permission(
                this.getId(),
                this.getName(),
                this.getDescription()
        );
    }
}