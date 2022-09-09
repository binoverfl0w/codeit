package org.example.codeit.domaintest.stepdefs;

import hexarch.AccessDeniedException;
import hexarch.Constants.*;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.example.codeit.domain.api.ForManagingProfile;
import org.example.codeit.domain.profile.BadCredentialsException;
import org.example.codeit.domain.profile.Permission;
import org.example.codeit.domain.profile.Profile;
import org.example.codeit.domain.profile.Role;
import org.example.codeit.domain.spi.AuthenticationContext;
import org.example.codeit.domain.spi.ForStoringProfile;
import org.example.codeit.domain.spi.stubs.TestContext;

import java.util.HashSet;

public class ProfileStepdefs {
    private TestContext testContext;
    private AuthenticationContext authenticationContext;

    private ForManagingProfile profileManager;
    private ForStoringProfile profileStore;

    public ProfileStepdefs(TestContext testContext, AuthenticationContext authenticationContext, ForManagingProfile profileManager, ForStoringProfile profileStore) {
        this.testContext = testContext;
        this.authenticationContext = authenticationContext;
        this.profileManager = profileManager;
        this.profileStore = profileStore;
    }

    @Before()
    public void setup() {
        Permission[] permissions = {new Permission(PERMISSIONS.MANAGE_PROFILE)};
        profileStore.storePermission(permissions[0]);
        profileStore.storeRole(new Role(ROLES.ROLE_CODER, new HashSet<>(){{ }}));
        profileStore.storeRole(new Role("ROLE_ADMIN", new HashSet<>(){{ add(permissions[0]); }}));
//        profileStore.storeProfile(new Profile("Arjan", "arjan", "arjan@gmail.com", "test", null));
//        System.out.println();
    }

    @Given("^an unauthenticated user$")
    public void givenAnUnauthenticatedUser() {
        authenticationContext.setAuthenticatedProfile(null);
        assert(authenticationContext.getAuthenticatedProfile() == null);
    }

    @When("^he creates a profile$")
    public void whenHeCreatesAProfile() {
        Profile profile = new Profile("Arjan","arjan","arjan@gmail.com","test",null);
        this.profileManager.createProfile(profile);
//        this.profileObtainer.createProfile(profile);
    }

    @Then("^a profile with role coder is stored$")
    public void thenAProfileWithRoleCoderIsStored() {
        assert(this.profileStore.getProfileByUsername("arjan").isPresent());
        assert(this.profileStore.getProfileByUsername("arjan").get().getRole().getName().equals(ROLES.ROLE_CODER));
    }

    @And("^a profile for him exists$")
    public void andAProfileWithThatUsernameExists() {
        Profile profile = new Profile("Arjan","arjan","arjan@gmail.com","test",null);
        this.profileManager.createProfile(profile);
        assert(this.profileStore.getProfileByUsername("arjan").isPresent());
    }

    @When("^username and password are valid$")
    public void whenHeAttemptsToLogin() {
        this.authenticationContext.login("arjan","test");
    }

    @Then("^profile context will hold that user$")
    public void thenProfileContextWillHoldThatUser() {
        assert(this.authenticationContext.getAuthenticatedProfile().getUsername().equals("arjan"));
    }

    @Given("^a user does not have permission$")
    public void givenAUserDoesNotHavePermission() {
        Profile profile = new Profile("Arjan","arjan","arjan@gmail.com","test",null);
        testContext.profile = profileManager.createProfile(profile);
        authenticationContext.setAuthenticatedProfile(testContext.profile);
        assert(!authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_PROFILE));
    }

    @When("^he updates his profile with another role$")
    public void whenHeUpdatesHisProfileWithAnotherRole() {
        assert(profileStore.getRoleByName("ROLE_ADMIN").isPresent());
        testContext.profile = profileManager.updateProfile(new Profile(testContext.profile.getId(),"Arjan","arjan","arjan@gmail.com","test",profileStore.getRoleByName("ROLE_ADMIN").get()));
    }

    @Then("^role update is ignored$")
    public void thenRoleUpdateIsIgnored() {
        assert(testContext.profile.getRole().getName().equals(ROLES.ROLE_CODER));
    }

    @Given("^a user with manage profile permission$")
    public void givenAUserWithManageProfilePermissions() {
        assert(profileStore.getRoleByName("ROLE_ADMIN").isPresent());
        Profile profile = new Profile("Arjan","arjan","arjan@gmail.com","test",profileStore.getRoleByName("ROLE_ADMIN").get());
        authenticationContext.setAuthenticatedProfile(profileStore.storeProfile(profile));
        assert(authenticationContext.ensurePermissions(PERMISSIONS.MANAGE_PROFILE));
    }

    @When("^he creates another profile with different role$")
    public void whenHeCreatesAnotherProfileWithDifferentRole() {
        assert(profileStore.getRoleByName("ROLE_ADMIN").isPresent());
        Profile toCreate = new Profile("Arjan2","arjan2","arjan2@gmail.com","test",profileStore.getRoleByName("ROLE_ADMIN").get());
        testContext.profile = profileManager.createProfile(toCreate);
    }

    @Then("^role is set successfully$")
    public void thenRoleIsSetSuccessfully() {
        assert(testContext.profile.getRole().getName().equals("ROLE_ADMIN"));
    }

    @When("^password is not valid$")
    public void whenPasswordIsNotValid() {
        try {
            authenticationContext.login("arjan","testwrong");
        } catch (BadCredentialsException ex) {
            testContext.exception = ex;
        }
    }

    @Then("^bad credentials exception is thrown$")
    public void thenBadCredentialsIsThrown() {
        assert(testContext.exception instanceof BadCredentialsException);
    }
}
