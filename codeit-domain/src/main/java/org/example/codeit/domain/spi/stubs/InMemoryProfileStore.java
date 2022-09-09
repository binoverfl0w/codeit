package org.example.codeit.domain.spi.stubs;

import hexarch.Constants.*;
import hexarch.AbstractPage;
import hexarch.AbstractPageImpl;
import org.example.codeit.domain.profile.Permission;
import org.example.codeit.domain.profile.Profile;
import org.example.codeit.domain.profile.Role;
import org.example.codeit.domain.spi.ForStoringProfile;

import java.util.HashMap;
import java.util.Optional;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;

public class InMemoryProfileStore implements ForStoringProfile {
    HashMap<Long, Profile> profiles = new HashMap<>();
    HashMap<Long, Role> roles = new HashMap<>();
    HashMap<Long, Permission> permissions = new HashMap<>();

    @Override
    public AbstractPage<Profile> getProfilesPage(int page, int size) {
        return new AbstractPageImpl<Profile>(
                profiles.size(),
                profiles.size() / size,
                page,
                profiles.values().stream().skip((long) page * size).limit(size).collect(Collectors.toList())
        );
    }

    @Override
    public Profile storeProfile(Profile profile) {
        if (profile.getId() == null) {
            Long id = RandomGenerator.getDefault().nextLong();
            profile.setId(id);
        }
        profiles.put(profile.getId(), profile);
        return profiles.get(profile.getId());
    }

    @Override
    public Optional<Profile> getProfileById(Long id) {
        return Optional.of(profiles.get(id));
    }

    @Override
    public Optional<Profile> getProfileByUsername(String username) {
        return profiles.values().stream().filter(p -> p.getUsername().equals(username)).findFirst();
    }

    @Override
    public void deleteProfileById(Long id) {
        profiles.remove(id);
    }

    @Override
    public Role storeRole(Role role) {
        if (role.getId() == null) {
            Long id = RandomGenerator.getDefault().nextLong();
            role.setId(id);
        }
        roles.put(role.getId(), role);
        return roles.get(role.getId());
    }

    @Override
    public AbstractPage<Role> getRolesPage(int page, int size) {
        return new AbstractPageImpl<Role>(
                roles.size(),
                roles.size() / size,
                page,
                roles.values().stream().skip((long) page * size).limit(size).collect(Collectors.toList())
        );
    }

    @Override
    public Optional<Role> getRoleByName(String name) {
        return roles.values().stream().filter(r -> r.getName().equals(name)).findFirst();
    }

    @Override
    public Optional<Role> getRoleById(Long id) {
        return Optional.of(roles.get(id));
    }

    @Override
    public void deleteRoleById(Long id) {
        roles.remove(id);
    }

    @Override
    public AbstractPage<Permission> getPermissionsPage(int page, int size) {
        return new AbstractPageImpl<Permission>(
                permissions.size(),
                permissions.size() / size,
                page,
                permissions.values().stream().skip((long) page * size).limit(size).collect(Collectors.toList())
        );
    }

    @Override
    public Permission storePermission(Permission permission) {
        if (permission.getId() == null) {
            Long id = RandomGenerator.getDefault().nextLong();
            permission.setId(id);
        }
        permissions.put(permission.getId(), permission);
        return permissions.get(permission.getId());
    }

    @Override
    public Optional<Permission> getPermissionById(Long id) {
        return Optional.of(permissions.get(id));
    }

    @Override
    public Optional<Permission> getPermissionByName(String name) {
        return permissions.values().stream().filter(p -> p.getName().equals(name)).findFirst();
    }

    @Override
    public void deletePermissionById(Long id) {
        permissions.remove(id);
    }

    @Override
    public Role getDefaultRole() {
        // TODO: initialize a default role
        return roles.values().stream().filter(r -> r.getName().equals(ROLES.ROLE_CODER)).findFirst().orElse(null);
    }

    @Override
    public Optional<Profile> findProfileByUsernameOrEmail(String usernameOrEmail) {
        return profiles.values().stream().filter(p -> p.getUsername().equals(usernameOrEmail) || p.getEmail().equals(usernameOrEmail)).findFirst();
    }
}
