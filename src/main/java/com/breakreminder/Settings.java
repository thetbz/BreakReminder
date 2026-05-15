package com.breakreminder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import javax.swing.JOptionPane;

public class Settings {

    private static final int DEFAULT_WORK_MINUTES = 45;
    private static final int DEFAULT_BREAK_MINUTES = 2;
    private static final String WORK_MINUTES_KEY = "work.minutes";
    private static final String BREAK_MINUTES_KEY = "break.minutes";
    private static final Path CONFIG_DIR = Path.of(System.getProperty("user.home"), ".breakreminder");
    private static final Path CONFIG_FILE = CONFIG_DIR.resolve("settings.properties");

    private final int workMinutes;
    private final int breakMinutes;

    private Settings(int workMinutes, int breakMinutes) {
        this.workMinutes = workMinutes;
        this.breakMinutes = breakMinutes;
    }

    public static Settings loadOrCreate() {
        return promptAndSave();
    }

    private static Settings promptAndSave() {
        int workMinutes = promptMinutes("Minutos de trabajo", "Introduce los minutos de trabajo:", DEFAULT_WORK_MINUTES);
        int breakMinutes = promptMinutes("Minutos de descanso", "Introduce los minutos de descanso:", DEFAULT_BREAK_MINUTES);
        Settings settings = new Settings(workMinutes, breakMinutes);
        save(settings);
        return settings;
    }

    private static int promptMinutes(String title, String message, int defaultMinutes) {
        while (true) {
            String input = JOptionPane.showInputDialog(null, message, title, JOptionPane.QUESTION_MESSAGE);
            if (input == null) {
                return defaultMinutes;
            }
            input = input.trim();
            if (input.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor ingresa un número válido.", "Valor inválido", JOptionPane.WARNING_MESSAGE);
                continue;
            }
            try {
                int minutes = Integer.parseInt(input);
                if (minutes < 1) {
                    JOptionPane.showMessageDialog(null, "El valor debe ser mayor que cero.", "Valor inválido", JOptionPane.WARNING_MESSAGE);
                    continue;
                }
                return minutes;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Por favor ingresa un número entero.", "Valor inválido", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    private static int parseMinutes(String value, int defaultValue) {
        if (value == null || value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            int minutes = Integer.parseInt(value.trim());
            return Math.max(1, minutes);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private static void save(Settings settings) {
        try {
            if (!Files.exists(CONFIG_DIR)) {
                Files.createDirectories(CONFIG_DIR);
            }
            Properties properties = new Properties();
            properties.setProperty(WORK_MINUTES_KEY, String.valueOf(settings.workMinutes));
            properties.setProperty(BREAK_MINUTES_KEY, String.valueOf(settings.breakMinutes));
            properties.store(Files.newOutputStream(CONFIG_FILE, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING), "Break Reminder settings");
        } catch (IOException e) {
            System.err.println("No se pudo guardar la configuración: " + e.getMessage());
        }
    }

    public int getWorkMinutes() {
        return workMinutes;
    }

    public int getBreakMinutes() {
        return breakMinutes;
    }
}
