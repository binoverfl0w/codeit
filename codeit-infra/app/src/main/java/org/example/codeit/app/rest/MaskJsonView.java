package org.example.codeit.app.rest;

import hexarch.Constants.*;
import org.example.codeit.app.rest.problem.dto.CategoriesDTO;
import org.example.codeit.app.rest.problem.dto.CategoryDTO;
import org.example.codeit.app.rest.problem.dto.ProblemDTO;
import org.example.codeit.app.rest.problem.dto.ProblemsDTO;
import org.example.codeit.app.rest.profile.dto.*;
import org.example.codeit.domain.spi.AuthenticationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
class MaskJsonView extends AbstractMappingJacksonResponseBodyAdvice {

    @Autowired @Lazy
    private AuthenticationContext authenticationContext;
    @Override
    protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType, MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
        Class jsonView = View.MAPPING.get("Default");
        if (bodyContainer.getValue() instanceof ProfilesDTO || bodyContainer.getValue() instanceof ProfileDTO) {
            if (authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_PROFILE)) jsonView = View.MAPPING.get("Privileged");
        }
        if (bodyContainer.getValue() instanceof RolesDTO || bodyContainer.getValue() instanceof RoleDTO) {
            if (authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_ROLES)) jsonView = View.MAPPING.get("Privileged");
        }
        if (bodyContainer.getValue() instanceof PermissionsDTO || bodyContainer.getValue() instanceof PermissionDTO) {
            if (authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_PERMISSIONS)) jsonView = View.MAPPING.get("Privileged");
        }
        if (bodyContainer.getValue() instanceof ProblemsDTO || bodyContainer.getValue() instanceof ProblemDTO) {
            if (authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_PROBLEMS)) jsonView = View.MAPPING.get("Privileged");
        }
        if (bodyContainer.getValue() instanceof CategoriesDTO || bodyContainer.getValue() instanceof CategoryDTO) {
            if (authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_CATEGORIES)) jsonView = View.MAPPING.get("Privileged");
        }
        if (jsonView != null) bodyContainer.setSerializationView(jsonView);
    }
}