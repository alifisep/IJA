package ija.ijaProject.settings;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Manages application settings with persistence.
 * Uses the Singleton pattern to ensure a single instance throughout the application.
 */
public class SettingsManager {
    private static final String SETTINGS_FILE = "voltmaze_settings.properties";
    private static SettingsManager instance;
    private Properties properties;

    // Default settings
    private static final Map<String, String> DEFAULT_SETTINGS = new HashMap<>() {{
        put("language", "en"); // Default language: English
        put("soundEnabled", "true"); // Sounds enabled by default
        put("soundVolume", "0.7"); // 70% volume by default
        put("musicEnabled", "true"); // Music enabled by default
        put("musicVolume", "0.5"); // 50% volume by default
    }};

    /**
     * Gets the singleton instance of SettingsManager.
     *
     * @return The SettingsManager instance
     */
    public static SettingsManager getInstance() {
        if (instance == null) {
            instance = new SettingsManager();
        }
        return instance;
    }

    /**
     * Private constructor to enforce singleton pattern.
     * Loads settings when created.
     */
    private SettingsManager() {
        properties = new Properties();
        loadSettings();
    }

    /**
     * Loads settings from file or creates default settings if file doesn't exist.
     */
    private void loadSettings() {
        File file = new File(SETTINGS_FILE);
        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                properties.load(fis);
                System.out.println("Settings loaded successfully");
            } catch (IOException e) {
                System.err.println("Error loading settings: " + e.getMessage());
                setDefaultSettings();
            }
        } else {
            setDefaultSettings();
        }
    }

    /**
     * Sets all settings to their default values.
     */
    private void setDefaultSettings() {
        DEFAULT_SETTINGS.forEach((key, value) -> properties.setProperty(key, value));
        System.out.println("Default settings applied");
        saveSettings();
    }

    /**
     * Saves current settings to file.
     */
    public void saveSettings() {
        try (FileOutputStream fos = new FileOutputStream(SETTINGS_FILE)) {
            properties.store(fos, "VoltMaze Settings");
            System.out.println("Settings saved successfully");
        } catch (IOException e) {
            System.err.println("Error saving settings: " + e.getMessage());
        }
    }

    /**
     * Gets a setting value.
     *
     * @param key The setting key
     * @return The setting value, or default if not found
     */
    public String getSetting(String key) {
        return properties.getProperty(key, DEFAULT_SETTINGS.getOrDefault(key, ""));
    }

    /**
     * Sets a setting value.
     *
     * @param key The setting key
     * @param value The setting value
     */
    public void setSetting(String key, String value) {
        properties.setProperty(key, value);
        saveSettings();
    }

    /**
     * Gets a boolean setting value.
     *
     * @param key The setting key
     * @return The boolean value of the setting
     */
    public boolean getBooleanSetting(String key) {
        return Boolean.parseBoolean(getSetting(key));
    }

    /**
     * Gets a double setting value.
     *
     * @param key The setting key
     * @return The double value of the setting
     */
    public double getDoubleSetting(String key) {
        try {
            return Double.parseDouble(getSetting(key));
        } catch (NumberFormatException e) {
            return Double.parseDouble(DEFAULT_SETTINGS.getOrDefault(key, "0.0"));
        }
    }

    /**
     * Resets all settings to their default values.
     */
    public void resetSettings() {
        setDefaultSettings();
    }
}