package ija.ijaProject.settings;

import java.io.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Manages language resources and translations.
 * Uses the Singleton pattern to ensure a single instance throughout the application.
 */
public class LanguageManager {
    private static LanguageManager instance;
    private ResourceBundle resources;
    private String currentLanguage;

    // Available languages
    public static final Map<String, String> AVAILABLE_LANGUAGES = new HashMap<>() {{
        put("en", "English");
        put("cs", "Čeština"); // Czech
    }};

    /**
     * Gets the singleton instance of LanguageManager.
     *
     * @return The LanguageManager instance
     */
    public static LanguageManager getInstance() {
        if (instance == null) {
            instance = new LanguageManager();
        }
        return instance;
    }

    /**
     * Private constructor to enforce singleton pattern.
     * Loads the language based on settings.
     */
    private LanguageManager() {
        // Get language from settings
        currentLanguage = SettingsManager.getInstance().getSetting("language");
        loadLanguage(currentLanguage);
    }

    /**
     * Loads a language resource bundle.
     *
     * @param languageCode The language code to load
     */
    public void loadLanguage(String languageCode) {
        try {
            System.out.println("Loading language: " + languageCode);

            Locale locale = new Locale(languageCode);

            // For your structure, the bundle name should be just "strings"
            resources = ResourceBundle.getBundle("strings", locale);

            currentLanguage = languageCode;
            SettingsManager.getInstance().setSetting("language", languageCode);
            System.out.println("Language loaded: " + AVAILABLE_LANGUAGES.get(languageCode));
        } catch (Exception e) {
            System.err.println("Error loading language: " + e.getMessage());
            e.printStackTrace();

            // Fall back to English
            if (!languageCode.equals("en")) {
                System.out.println("Falling back to English");
                loadLanguage("en");
            } else {
                // If even English fails, create a default resource bundle in memory
                System.out.println("Creating default English resources");
                createDefaultResources();
            }
        }
    }

    /**
     * Creates default resources if no resource bundle is found.
     */
    private void createDefaultResources() {
        // Create a map-based resource bundle with default values
        resources = new ResourceBundle() {
            private final Map<String, String> defaultStrings = createDefaultStrings();

            @Override
            protected Object handleGetObject(String key) {
                return defaultStrings.get(key);
            }

            @Override
            public java.util.Enumeration<String> getKeys() {
                return java.util.Collections.enumeration(defaultStrings.keySet());
            }

            private Map<String, String> createDefaultStrings() {
                Map<String, String> map = new HashMap<>();

                // Main menu
                map.put("play", "Play");
                map.put("info", "Info");
                map.put("settings", "Settings");
                map.put("exit", "Exit");

                // Common
                map.put("back", "Back");
                map.put("cancel", "Cancel");
                map.put("ok", "OK");

                // Settings
                map.put("language_settings", "Language Settings");
                map.put("language", "Language");
                map.put("language_note", "Changes to language will take effect immediately.");
                map.put("sound_settings", "Sound Settings");
                map.put("sound_effects", "Sound Effects");
                map.put("background_music", "Background Music");
                map.put("volume", "Volume");
                map.put("sound_note", "Sound effects will be played during gameplay and UI interactions.");
                map.put("reset_settings", "Reset Settings");
                map.put("reset_settings_confirm", "Are you sure you want to reset all settings to default? This action cannot be undone.");
                map.put("reset_all", "Reset All");
                map.put("settings_reset_success", "Settings reset successfully!");

                // Levels
                map.put("select_level", "Select Level");
                map.put("beginner", "Beginner");
                map.put("intermediate", "Intermediate");
                map.put("advanced", "Advanced");
                map.put("level", "Level");
                map.put("reset_progress", "Reset Progress");
                map.put("reset_progress_confirm", "Are you sure you want to reset all level progress? This action cannot be undone.");
                map.put("progress_reset_success", "Progress reset successfully!");

                // Game
                map.put("level_completed", "Level Completed!");
                map.put("next_level", "Next Level");
                map.put("all_bulbs_lit", "All bulbs are lit! Great job!");

                return map;
            }
        };
    }

    /**
     * Gets a translated string for the given key.
     *
     * @param key The translation key
     * @return The translated string, or the key if not found
     */
    public String getString(String key) {
        try {
            return resources.getString(key);
        } catch (Exception e) {
            System.err.println("Missing translation for key: " + key);
            return key;
        }
    }

    /**
     * Gets the current language code.
     *
     * @return The current language code
     */
    public String getCurrentLanguage() {
        return currentLanguage;
    }

    /**
     * Gets the display name of the current language.
     *
     * @return The display name of the current language
     */
    public String getCurrentLanguageDisplayName() {
        return AVAILABLE_LANGUAGES.getOrDefault(currentLanguage, "Unknown");
    }
}