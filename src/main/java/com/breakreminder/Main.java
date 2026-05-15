package com.breakreminder;

/**
 * Main entry point for the Break Reminder application. Starts the automatic
 * work/break cycle reminder.
 */
public class Main {

    public static void main(String[] args) {
        Settings settings = Settings.loadOrCreate();
        BreakReminder breakReminder = new BreakReminder(settings.getWorkMinutes(), settings.getBreakMinutes());

        System.out.println("===========================================");
        System.out.println("     Work Break Reminder Application      ");
        System.out.println("===========================================");
        System.out.println("Work cycles: " + settings.getWorkMinutes() + " minutes");
        System.out.println("Break cycles: " + settings.getBreakMinutes() + " minutes");
        System.out.println("Click 'start' button in the GUI to begin");
        System.out.println("===========================================\n");
    }
}
