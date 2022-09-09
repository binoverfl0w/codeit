package org.example.codeit.app.jpa.profile.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.codeit.domain.profile.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "profile")
public class ProfileEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "fullname", nullable = false)
    private String fullname;
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "score", nullable = false)
    private int score = 0;
    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;
    @Column(name = "locked", nullable = false)
    private boolean locked = false;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new HashSet<>();
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
        authorities.add(authority);
        for (PermissionEntity permission : role.getPermissions()) {
            authorities.add(new SimpleGrantedAuthority(permission.getName()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public static ProfileEntity fromDomainProfile(Profile profile) {
        return new ProfileEntity(profile.getId(), profile.getFullname(), profile.getUsername(), profile.getEmail(), profile.getPassword(), profile.getScore(), profile.isEnabled(), profile.isLocked(), RoleEntity.fromDomainRole(profile.getRole()));
    }

    public Profile toDomainProfile() {
        return new Profile(this.getId(), this.getFullname(), this.getUsername(), this.getEmail(), this.getPassword(), this.getScore(), this.isEnabled(), this.isLocked(), this.getRole().toDomainRole());
    }
}
