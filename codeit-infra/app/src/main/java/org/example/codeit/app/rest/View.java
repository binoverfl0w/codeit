package org.example.codeit.app.rest;

import java.util.HashMap;
import java.util.Map;

public class View {
    public static final Map<String, Class> MAPPING = new HashMap<>();

    static {
        MAPPING.put("Default", Default.class);
        MAPPING.put("Privileged", Privileged.class);
    }

    public static class Default {}
    public static class Privileged extends Default {}
}
