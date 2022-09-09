package org.example.codeit.domain.api;

import hexarch.AbstractPage;
import org.example.codeit.domain.profile.Permission;
import org.example.codeit.domain.profile.Profile;
import org.example.codeit.domain.profile.Role;

public interface ForManagingProfile {
//    void loginProfile(String username, String password);

    Profile createProfile(Profile profile);

    Profile updateProfile(Profile profile);

    Profile getProfile(Long id);

    AbstractPage<Profile> getProfilesPage(int page, int size);

    void deleteProfile(Long id);

    Role createRole(Role role);

    Role updateRole(Role role);

    Role getRole(Long id);

    AbstractPage<Role> getRolesPage(int page, int size);

    void deleteRole(Long id);

    Permission createPermission(Permission permission);

    Permission updatePermission(Permission permission);

    Permission getPermission(Long id);

    AbstractPage<Permission> getPermissionsPage(int page, int size);

    void deletePermission(Long id);
}
