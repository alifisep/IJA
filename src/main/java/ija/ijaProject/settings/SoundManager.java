package ija.ijaProject.settings;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.*;

/**
 * Manages sound effects and background music.
 * Uses the Singleton pattern to ensure a single instance throughout the application.
 */
public class SoundManager {
    private static SoundManager instance;
    private final Map<String, File> soundFiles = new HashMap<>();
    private boolean soundEnabled;
    private boolean musicEnabled;
    private double soundVolume;
    private double musicVolume;
    private Clip currentMusicClip;

    /**
     * Gets the singleton instance of SoundManager.
     *
     * @return The SoundManager instance
     */
    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    /**
     * Private constructor to enforce singleton pattern.
     * Loads settings and initializes sounds.
     */
    private SoundManager() {
        // Load settings
        SettingsManager settings = SettingsManager.getInstance();
        soundEnabled = settings.getBooleanSetting("soundEnabled");
        musicEnabled = settings.getBooleanSetting("musicEnabled");
        soundVolume = settings.getDoubleSetting("soundVolume");
        musicVolume = settings.getDoubleSetting("musicVolume");

        // Load sound effects
        loadSoundEffects();
    }

    /**
     * Loads all sound effects.
     */
    private void loadSoundEffects() {
        loadSound("button_click", "sounds/button_click.wav");
        loadSound("level_complete", "sounds/level_complete.wav");
        loadSound("bulb_on", "sounds/bulb_on.wav");
        loadSound("rotate", "sounds/rotate.wav");
        loadSound("error", "sounds/error.wav");
    }

    /**
     * Loads a sound effect.
     *
     * @param name The name of the sound
     * @param path The path to the sound file
     */
    private void loadSound(String name, String path) {
        try {
            File soundFile = new File(path);
            if (soundFile.exists()) {
                soundFiles.put(name, soundFile);
                System.out.println("Sound loaded: " + name);
            } else {
                // Try to load from resources
                String resourcePath = getClass().getClassLoader().getResource(path).getPath();
                soundFile = new File(resourcePath);
                if (soundFile.exists()) {
                    soundFiles.put(name, soundFile);
                    System.out.println("Sound loaded from resources: " + name);
                } else {
                    System.err.println("Sound file not found: " + path);
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading sound: " + e.getMessage());
        }
    }

    /**
     * Plays a sound effect.
     *
     * @param name The name of the sound to play
     */
    public void playSound(String name) {
        if (!soundEnabled) return;

        File soundFile = soundFiles.get(name);
        if (soundFile != null) {
            try {
                // Create a new audio input stream
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

                // Get a clip resource
                Clip clip = AudioSystem.getClip();

                // Open audio clip and load samples from the audio input stream
                clip.open(audioInputStream);

                // Set volume (using a simple approximation since javax.sound doesn't have direct volume control)
                if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    float range = gainControl.getMaximum() - gainControl.getMinimum();
                    float gain = (float) (gainControl.getMinimum() + range * soundVolume);
                    gainControl.setValue(gain);
                }

                // Play the clip
                clip.start();

                // Add a line listener to release resources when done
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });
            } catch (Exception e) {
                System.err.println("Error playing sound: " + e.getMessage());
            }
        }
    }

    /**
     * Plays background music.
     *
     * @param path The path to the music file
     */
    public void playMusic(String path) {
        if (!musicEnabled) return;

        try {
            stopMusic();

            File musicFile = new File(path);
            if (!musicFile.exists()) {
                // Try to load from resources
                String resourcePath = getClass().getClassLoader().getResource(path).getPath();
                musicFile = new File(resourcePath);
            }

            if (musicFile.exists()) {
                // Create a new audio input stream
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);

                // Get a clip resource
                currentMusicClip = AudioSystem.getClip();

                // Open audio clip and load samples from the audio input stream
                currentMusicClip.open(audioInputStream);

                // Set volume
                if (currentMusicClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    FloatControl gainControl = (FloatControl) currentMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
                    float range = gainControl.getMaximum() - gainControl.getMinimum();
                    float gain = (float) (gainControl.getMinimum() + range * musicVolume);
                    gainControl.setValue(gain);
                }

                // Loop continuously
                currentMusicClip.loop(Clip.LOOP_CONTINUOUSLY);

                System.out.println("Music started: " + path);
            } else {
                System.err.println("Music file not found: " + path);
            }
        } catch (Exception e) {
            System.err.println("Error playing music: " + e.getMessage());
        }
    }

    /**
     * Stops the currently playing background music.
     */
    public void stopMusic() {
        if (currentMusicClip != null && currentMusicClip.isRunning()) {
            currentMusicClip.stop();
            currentMusicClip.close();
            currentMusicClip = null;
        }
    }

    /**
     * Sets whether sound effects are enabled.
     *
     * @param enabled True to enable sound effects, false to disable
     */
    public void setSoundEnabled(boolean enabled) {
        this.soundEnabled = enabled;
        SettingsManager.getInstance().setSetting("soundEnabled", String.valueOf(enabled));
    }

    /**
     * Sets whether background music is enabled.
     *
     * @param enabled True to enable background music, false to disable
     */
    public void setMusicEnabled(boolean enabled) {
        this.musicEnabled = enabled;
        SettingsManager.getInstance().setSetting("musicEnabled", String.valueOf(enabled));

        if (!enabled && currentMusicClip != null) {
            stopMusic();
        }
    }

    /**
     * Sets the volume for sound effects.
     *
     * @param volume The volume level (0.0 to 1.0)
     */
    public void setSoundVolume(double volume) {
        this.soundVolume = volume;
        SettingsManager.getInstance().setSetting("soundVolume", String.valueOf(volume));
    }

    /**
     * Sets the volume for background music.
     *
     * @param volume The volume level (0.0 to 1.0)
     */
    public void setMusicVolume(double volume) {
        this.musicVolume = volume;
        SettingsManager.getInstance().setSetting("musicVolume", String.valueOf(volume));

        // Update current music volume if playing
        if (currentMusicClip != null && currentMusicClip.isRunning() &&
                currentMusicClip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl gainControl = (FloatControl) currentMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (float) (gainControl.getMinimum() + range * musicVolume);
            gainControl.setValue(gain);
        }
    }

    /**
     * Gets whether sound effects are enabled.
     *
     * @return True if sound effects are enabled, false otherwise
     */
    public boolean isSoundEnabled() {
        return soundEnabled;
    }

    /**
     * Gets whether background music is enabled.
     *
     * @return True if background music is enabled, false otherwise
     */
    public boolean isMusicEnabled() {
        return musicEnabled;
    }

    /**
     * Gets the volume for sound effects.
     *
     * @return The volume level (0.0 to 1.0)
     */
    public double getSoundVolume() {
        return soundVolume;
    }

    /**
     * Gets the volume for background music.
     *
     * @return The volume level (0.0 to 1.0)
     */
    public double getMusicVolume() {
        return musicVolume;
    }
}