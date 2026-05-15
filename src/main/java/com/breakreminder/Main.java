package com.breakreminder;

/**
 * Main entry point for the Break Reminder application.
 * Starts the automatic work/break cycle reminder.
 */
public class Main {
    
    public static void main(String[] args) {
        // Create and start the reminder
        BreakReminder breakReminder = new BreakReminder();
        
        System.out.println("===========================================");
        System.out.println("     Work Break Reminder Application      ");
        System.out.println("===========================================");
        System.out.println("Work cycles: 45 minutes");
        System.out.println("Break cycles: 2 minutes");
        System.out.println("Click 'Start' button in the GUI to begin");
        System.out.println("===========================================\n");
    }
}
