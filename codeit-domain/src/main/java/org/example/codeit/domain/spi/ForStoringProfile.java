package org.example.codeit.domain.spi;

import hexarch.AbstractPage;
import org.example.codeit.domain.profile.Permission;
import org.example.codeit.domain.profile.Profile;
import org.example.codeit.domain.profile.Role;

import java.util.Optional;

public interface ForStoringProfile {
    AbstractPage<Profile> getProfilesPage(int page, int size);

    Profile storeProfile(Profile profile);

    Optional<Profile> getProfileById(Long id);

    Optional<Profile> getProfileByUsername(String username);

    void deleteProfileById(Long id);

    Role storeRole(Role role);

    AbstractPage<Role> getRolesPage(int page, int size);

    Optional<Role> getRoleByName(String name);

    Optional<Role> getRoleById(Long id);

    void deleteRoleById(Long id);

    AbstractPage<Permission> getPermissionsPage(int page, int size);

    Permission storePermission(Permission permission);

    Optional<Permission> getPermissionById(Long id);

    Optional<Permission> getPermissionByName(String name);

    void deletePermissionById(Long id);

    Role getDefaultRole();

    Optional<Profile> findProfileByUsernameOrEmail(String usernameOrEmail);
}
