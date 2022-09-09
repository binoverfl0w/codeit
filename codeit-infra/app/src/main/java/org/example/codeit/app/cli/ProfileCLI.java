package org.example.codeit.app.cli;

import org.example.codeit.domain.api.ForManagingProblem;
import org.example.codeit.domain.api.ForManagingProfile;
import org.example.codeit.domain.profile.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ProfileCLI implements CommandLineRunner {

    private ForManagingProfile profileManager;

    @Autowired
    public ProfileCLI(ForManagingProfile profileManager) {
        this.profileManager = profileManager;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner sc = new Scanner(System.in);
        Long id = sc.nextLong();
        Profile profile = profileManager.getProfile(id);
        System.out.println(profile.getId() + " | " + profile.getFullname() + " | " + profile.getScore());
    }
}
