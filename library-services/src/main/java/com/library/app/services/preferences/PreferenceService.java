package com.library.app.services.preferences;

import java.util.List;
import java.util.Set;

public interface PreferenceService {
    void addUserPreference(String principalName, String preference);
    void addUserPreferences(String principalName, List<String> preferences);

    Set<String> getUserPreferences(String principalName);
}
