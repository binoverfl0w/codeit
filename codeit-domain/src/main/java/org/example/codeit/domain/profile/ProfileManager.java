package org.example.codeit.domain.profile;

import hexarch.AccessDeniedException;
import hexarch.Constants.*;
import hexarch.AbstractPage;
import org.example.codeit.domain.api.ForManagingProfile;
import org.example.codeit.domain.spi.AuthenticationContext;
import org.example.codeit.domain.spi.ForStoringProfile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

public class ProfileManager implements ForManagingProfile {

    private ForStoringProfile profileStore;
    private AuthenticationContext authenticationContext;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ProfileManager(ForStoringProfile profileStore, AuthenticationContext authenticationContext) {
        this.profileStore = profileStore;
        this.authenticationContext = authenticationContext;
    }

    @Override
    public Profile createProfile(Profile profile) {
        if (!authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_PROFILE)) {
            profile.setRole(profileStore.getDefaultRole());
            profile.setEnabled(true);
            profile.setLocked(false);
            profile.setScore(0);
        }
        else profileStore.getRoleByName(profile.getRole().getName()).orElseThrow(() -> new RoleNotFoundException("name", profile.getRole().getName()));
        profile.setPassword(passwordEncoder.encode(profile.getPassword()));
        return profileStore.storeProfile(profile);
    }

    @Override
    public Profile updateProfile(Profile profile) {
        // TODO update fields that are available in request
        Profile toUpdate = profileStore.getProfileById(profile.getId()).orElseThrow(() -> new ProfileNotFoundException("id", profile.getId().toString()));
        if (authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_PROFILE)) {
            if (profile.getRole() != null) toUpdate.setRole(profileStore.getRoleByName(profile.getRole().getName()).orElseThrow(() -> new RoleNotFoundException("name", profile.getRole().getName())));
            if (profile.isLocked() != null) toUpdate.setLocked(profile.isLocked());
        } else if (authenticationContext.getAuthenticatedProfile() == null || !profile.getId().equals(authenticationContext.getAuthenticatedProfile().getId()))
            throw new AccessDeniedException();
        // TODO Check unique constraint
        if (profile.getUsername() != null) toUpdate.setUsername(profile.getUsername());
        if (profile.getEmail() != null) toUpdate.setEmail(profile.getEmail());

        if (profile.getFullname() != null) toUpdate.setFullname(profile.getFullname());
        if (profile.getPassword() != null) toUpdate.setPassword(passwordEncoder.encode(profile.getPassword()));
        return profileStore.storeProfile(toUpdate);
    }

    @Override
    public Profile getProfile(Long id) {
        return profileStore.getProfileById(id).orElseThrow(() -> new ProfileNotFoundException("id", id.toString() ));
    }

    @Override
    public AbstractPage<Profile> getProfilesPage(int page, int size) {
        return profileStore.getProfilesPage(page, size);
    }

    @Override
    public void deleteProfile(Long id) {
        if (!authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_PROFILE)) throw new AccessDeniedException();
        profileStore.deleteProfileById(id);
    }

    @Override
    public Role createRole(Role role) {
        if (!authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_ROLES)) throw new AccessDeniedException();
        role.getPermissions().forEach(p -> profileStore.getPermissionById(p.getId()).orElseThrow(() -> new PermissionNotFoundException("name",p.getName())));
        return profileStore.storeRole(role);
    }

    @Override
    public Role updateRole(Role role) {
        if (!authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_ROLES)) throw new AccessDeniedException();
        Role toUpdate = profileStore.getRoleById(role.getId()).orElseThrow(() -> new RoleNotFoundException("id", role.getId().toString()));
        // TODO check constraints
        if (role.getName() != null) toUpdate.setName(role.getName());
        if (role.getPermissions() != null) {
            Set<Permission> permissions = new HashSet<>();
            role.getPermissions().forEach(p -> permissions.add(profileStore.getPermissionByName(p.getName()).orElseThrow(() -> new PermissionNotFoundException("name", p.getName()))));
            toUpdate.setPermissions(permissions);
        }
        return profileStore.storeRole(toUpdate);
    }

    @Override
    public Role getRole(Long id) {
        if (!authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_ROLES)) throw new AccessDeniedException();
        return profileStore.getRoleById(id).orElseThrow(() -> new RoleNotFoundException("id", id.toString()));
    }

    @Override
    public AbstractPage<Role> getRolesPage(int page, int size) {
        if (!authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_ROLES)) throw new AccessDeniedException();
        return profileStore.getRolesPage(page, size);
    }

    @Override
    public void deleteRole(Long id) {
        if (!authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_ROLES)) throw new AccessDeniedException();
        profileStore.deleteRoleById(id);
    }

    @Override
    public Permission createPermission(Permission permission) {
        if (!authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_PERMISSIONS)) throw new AccessDeniedException();
        return profileStore.storePermission(permission);
    }

    @Override
    public Permission updatePermission(Permission permission) {
        if (!authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_PERMISSIONS)) throw new AccessDeniedException();
        Permission toUpdate = profileStore.getPermissionById(permission.getId()).orElseThrow(() -> new PermissionNotFoundException("id", permission.getId().toString()));
        // TODO check constraint
        if (permission.getName() != null) toUpdate.setName(permission.getName());
        if (permission.getDescription() != null) toUpdate.setDescription(permission.getDescription());
        return profileStore.storePermission(toUpdate);
    }

    @Override
    public Permission getPermission(Long id) {
        if (!authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_PERMISSIONS)) throw new AccessDeniedException();
        return profileStore.getPermissionById(id).orElseThrow(() -> new PermissionNotFoundException("id", id.toString()));
    }

    @Override
    public AbstractPage<Permission> getPermissionsPage(int page, int size) {
        if (!authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_PERMISSIONS)) throw new AccessDeniedException();
        return profileStore.getPermissionsPage(page, size);
    }

    @Override
    public void deletePermission(Long id) {
        if (!authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_PERMISSIONS)) throw new AccessDeniedException();
        profileStore.deletePermissionById(id);
    }
}
