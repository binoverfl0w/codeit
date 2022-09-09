package org.example.codeit.app.jpa.profile;

import hexarch.AbstractPage;
import hexarch.AbstractPageImpl;
import hexarch.Constants.*;
import org.example.codeit.app.jpa.profile.entities.PermissionEntity;
import org.example.codeit.app.jpa.profile.entities.ProfileEntity;
import org.example.codeit.app.jpa.profile.entities.RoleEntity;
import org.example.codeit.app.jpa.profile.repositories.PermissionRepository;
import org.example.codeit.app.jpa.profile.repositories.ProfileRepository;
import org.example.codeit.app.jpa.profile.repositories.RoleRepository;
import org.example.codeit.domain.profile.Permission;
import org.example.codeit.domain.profile.Profile;
import org.example.codeit.domain.profile.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.stream.Collectors;

public class ProfileStore implements CombinedProfileInterface {

    private ProfileRepository profileRepository;
    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;

    @Autowired
    public ProfileStore(ProfileRepository profileRepository, RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.profileRepository = profileRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    public AbstractPage<Profile> getProfilesPage(int page, int size) {
        Page<ProfileEntity> profilesPage = profileRepository.findAll(PageRequest.of(page, size));
        return new AbstractPageImpl<>(
                profilesPage.getTotalElements(),
                profilesPage.getTotalPages(),
                profilesPage.getNumber(),
                profilesPage.getContent().stream().map(ProfileEntity::toDomainProfile).collect(Collectors.toList())
        );
    }

    @Override
    public Profile storeProfile(Profile profile) {
        return profileRepository.save(ProfileEntity.fromDomainProfile(profile)).toDomainProfile();
    }

    @Override
    public Optional<Profile> getProfileById(Long id) {
        return profileRepository.findById(id).map(ProfileEntity::toDomainProfile);
    }

    @Override
    public Optional<Profile> getProfileByUsername(String username) {
        return Optional.of(profileRepository.findByUsername(username).toDomainProfile());
    }

    @Override
    public void deleteProfileById(Long id) {
        profileRepository.deleteById(id);
    }

    @Override
    public Role storeRole(Role role) {
        return roleRepository.save(RoleEntity.fromDomainRole(role)).toDomainRole();
    }

    @Override
    public AbstractPage<Role> getRolesPage(int page, int size) {
        Page<RoleEntity> rolesPage = roleRepository.findAll(PageRequest.of(page, size));
        return new AbstractPageImpl<>(
                rolesPage.getTotalElements(),
                rolesPage.getTotalPages(),
                rolesPage.getNumber(),
                rolesPage.getContent().stream().map(RoleEntity::toDomainRole).collect(Collectors.toList())
        );
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        return Optional.of(roleRepository.findByName(name).toDomainRole());
    }

    @Override
    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id).map(RoleEntity::toDomainRole);
    }

    @Override
    public void deleteRoleById(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public AbstractPage<Permission> getPermissionsPage(int page, int size) {
        Page<PermissionEntity> permissionsPage = permissionRepository.findAll(PageRequest.of(page, size));
        return new AbstractPageImpl<Permission>(
                permissionsPage.getTotalElements(),
                permissionsPage.getTotalPages(),
                permissionsPage.getNumber(),
                permissionsPage.getContent().stream().map(PermissionEntity::toDomainPermission).collect(Collectors.toList())
        );
    }

    @Override
    public Permission storePermission(Permission permission) {
        return permissionRepository.save(PermissionEntity.fromDomainPermission(permission)).toDomainPermission();
    }

    @Override
    public Optional<Permission> getPermissionById(Long id) {
        return permissionRepository.findById(id).map(PermissionEntity::toDomainPermission);
    }

    @Override
    public Optional<Permission> getPermissionByName(String name) {
        return Optional.of(permissionRepository.findByName(name).toDomainPermission());
    }

    @Override
    public void deletePermissionById(Long id) {
        permissionRepository.deleteById(id);
    }

    @Override
    public Role getDefaultRole() {
        return roleRepository.findByName(ROLES.ROLE_CODER).toDomainRole();
    }

    @Override
    public Optional<Profile> findProfileByUsernameOrEmail(String usernameOrEmail) {
        return Optional.of(profileRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).toDomainProfile());
    }

    @Override
    public ProfileEntity loadUserByUsername(String username) throws UsernameNotFoundException {
        return profileRepository.findByUsernameOrEmail(username, username);
    }
}
