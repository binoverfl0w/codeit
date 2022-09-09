package org.example.codeit.app.jpa.profile.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.codeit.domain.profile.Role;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permission",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "permission_id")}
    )
    Set<PermissionEntity> permissions = new HashSet<>();

    public static RoleEntity fromDomainRole(Role role) {
        return new RoleEntity(role.getId(), role.getName(), role.getPermissions().stream().map(PermissionEntity::fromDomainPermission).collect(Collectors.toSet()));
    }

    public Role toDomainRole() {
        return new Role(this.getId(), this.getName(), this.getPermissions().stream().map(PermissionEntity::toDomainPermission).collect(Collectors.toSet()));
    }
}
