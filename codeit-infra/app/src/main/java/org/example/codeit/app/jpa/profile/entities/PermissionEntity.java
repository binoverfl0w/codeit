package org.example.codeit.app.jpa.profile.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.codeit.domain.profile.Permission;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permission")
public class PermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;

    public static PermissionEntity fromDomainPermission(org.example.codeit.domain.profile.Permission permission) {
        return new PermissionEntity(permission.getId(), permission.getName(), permission.getDescription());
    }

    public Permission toDomainPermission() {
        return new Permission(this.getId(), this.getName(), this.getDescription());
    }
}
