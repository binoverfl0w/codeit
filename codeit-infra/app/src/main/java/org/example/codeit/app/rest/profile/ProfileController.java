package org.example.codeit.app.rest.profile;

import hexarch.AbstractPage;
import org.example.codeit.app.jpa.profile.entities.ProfileEntity;
import org.example.codeit.app.rest.Response;
import org.example.codeit.app.rest.profile.dto.*;
import org.example.codeit.app.security.jwt.JWTTokenUtil;
import org.example.codeit.domain.api.ForManagingProfile;
import org.example.codeit.domain.profile.Permission;
import org.example.codeit.domain.profile.Profile;
import org.example.codeit.domain.profile.Role;
import org.example.codeit.domain.spi.AuthenticationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private ForManagingProfile profileManager;
    private AuthenticationContext authenticationContext;
    private JWTTokenUtil jwtTokenUtil;

    @Autowired
    public ProfileController(ForManagingProfile profileManager, AuthenticationContext authenticationContext, JWTTokenUtil jwtTokenUtil) {
        this.profileManager = profileManager;
        this.authenticationContext = authenticationContext;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @GetMapping
    public ResponseEntity<Object> getProfiles(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 20;
        AbstractPage<Profile> profiles = profileManager.getProfilesPage(page, size);
        return Response.handleGet(
                ProfilesDTO.builder()
                        .profiles(profiles.getItems().stream().map(ProfileDTO::fromDomainProfile).collect(Collectors.toList()))
                        .currentPage(profiles.getCurrentPage())
                        .totalPages(profiles.getTotalPages())
                        .totalItems(profiles.getTotalItems())
                        .build()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProfile(@PathVariable Long id) {
        return Response.handleGet(
                ProfileDTO.fromDomainProfile(profileManager.getProfile(id))
        );
    }

    @PostMapping
    public ResponseEntity<Object> signUp(@RequestBody ProfileDTO profileDTO) {
        return Response.handlePost(
                ProfileDTO.fromDomainProfile(profileManager.createProfile(profileDTO.toDomainProfile()))
        );
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody ProfileLoginDTO profileLoginDTO) {
        authenticationContext.login(profileLoginDTO.getUsername(), profileLoginDTO.getPassword());
        String token = jwtTokenUtil.generateToken(ProfileEntity.fromDomainProfile(authenticationContext.getAuthenticatedProfile()));
        return Response.handlePost(AuthTokenDTO.builder()
                .access_token(token)
                .token_type("Bearer")
                .expires_in(jwtTokenUtil.getValidPeriod())
                .build()
        );
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateProfile(@PathVariable Long id, @RequestBody ProfileDTO profileDTO) {
        profileDTO.setId(id);
        return Response.handlePatch(
                ProfileDTO.fromDomainProfile(profileManager.updateProfile(profileDTO.toDomainProfile()))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProfile(@PathVariable Long id) {
        profileManager.deleteProfile(id);
        return Response.handleDelete();
    }

    @GetMapping("/roles")
    public ResponseEntity<Object> getRoles(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 20;
        AbstractPage<Role> roles = profileManager.getRolesPage(page, size);
        return Response.handleGet(
                RolesDTO.builder()
                        .roles(roles.getItems().stream().map(RoleDTO::fromDomainRole).collect(Collectors.toList()))
                        .currentPage(roles.getCurrentPage())
                        .totalPages(roles.getTotalPages())
                        .totalItems(roles.getTotalItems())
                        .build()
        );
    }

    @GetMapping("/roles/{id}")
    public ResponseEntity<Object> getRole(@PathVariable Long id) {
        return Response.handleGet(
                RoleDTO.fromDomainRole(profileManager.getRole(id))
        );
    }

    @PostMapping("/roles")
    public ResponseEntity<Object> createRole(@RequestBody RoleDTO roleDTO) {
        return Response.handlePost(
                RoleDTO.fromDomainRole(profileManager.createRole(roleDTO.toDomainRole()))
        );
    }

    @PatchMapping("/roles/{id}")
    public ResponseEntity<Object> updateProfile(@PathVariable Long id, @RequestBody RoleDTO roleDTO) {
        //TODO check /roles/{id} with the id in request body
        return Response.handlePatch(
                RoleDTO.fromDomainRole(profileManager.updateRole(roleDTO.toDomainRole()))
        );
    }

    @DeleteMapping("/roles/{id}")
    public ResponseEntity<Object> deleteRole(@PathVariable Long id) {
        profileManager.deleteRole(id);
        return Response.handleDelete();
    }

    @GetMapping("/permissions")
    public ResponseEntity<Object> getPermissions(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 20;
        AbstractPage<Permission> permissions = profileManager.getPermissionsPage(page, size);
        return Response.handleGet(
                PermissionsDTO.builder()
                        .permissions(permissions.getItems().stream().map(PermissionDTO::fromDomainPermission).collect(Collectors.toList()))
                        .currentPage(permissions.getCurrentPage())
                        .totalPages(permissions.getTotalPages())
                        .totalItems(permissions.getTotalItems())
                        .build()
        );
    }

    @GetMapping("/permissions/{id}")
    public ResponseEntity<Object> getPermission(@PathVariable Long id) {
        return Response.handleGet(
                PermissionDTO.fromDomainPermission(profileManager.getPermission(id))
        );
    }

    @PostMapping("/permissions")
    public ResponseEntity<Object> createPermission(@RequestBody PermissionDTO permissionDTO) {
        return Response.handlePost(
                PermissionDTO.fromDomainPermission(profileManager.createPermission(permissionDTO.toDomainPermission()))
        );
    }

    @PatchMapping("/permissions/{id}")
    public ResponseEntity<Object> updatePermission(@PathVariable Long id, @RequestBody PermissionDTO permissionDTO) {
        //TODO check /permissions/{id} with id in request body
        return Response.handlePatch(
                PermissionDTO.fromDomainPermission(profileManager.updatePermission(permissionDTO.toDomainPermission()))
        );
    }

    @DeleteMapping("/permissions/{id}")
    public ResponseEntity<Object> deletePermission(@PathVariable Long id) {
        profileManager.deletePermission(id);
        return Response.handleDelete();
    }
}
