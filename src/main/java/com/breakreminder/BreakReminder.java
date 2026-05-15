package com.breakreminder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Main logic for managing work and break cycles.
 * Automatically cycles between 45-minute work sessions and 2-minute breaks.
 */
public class BreakReminder {
    private static final long WORK_TIME_MS = 45 * 60 * 1000;  // 45 minutes
    private static final long BREAK_TIME_MS = 2 * 60 * 1000;  // 2 minutes
    
    private ReminderGUI gui;
    private SoundAlert soundAlert;
    private Thread reminderThread;
    private boolean isRunning = false;
    private boolean isWorkTime = true;
    
    public BreakReminder() {
        this.gui = new ReminderGUI(this);
        this.soundAlert = new SoundAlert();
    }
    
    /**
     * Start the automatic reminder cycle
     */
    public void start() {
        if (isRunning) {
            gui.updateStatus("Already running");
            return;
        }
        
        isRunning = true;
        isWorkTime = true;
        gui.updateStatus("Work session started! (45 minutes)");
        
        reminderThread = new Thread(() -> {
            while (isRunning) {
                try {
                    if (isWorkTime) {
                        // Work for 45 minutes
                        long startTime = System.currentTimeMillis();
                        while (isRunning && isWorkTime) {
                            long elapsed = System.currentTimeMillis() - startTime;
                            long remaining = WORK_TIME_MS - elapsed;
                            
                            if (remaining <= 0) {
                                // Time for a break!
                                isWorkTime = false;
                                onBreakTime();
                                break;
                            }
                            
                            // Update GUI every second
                            gui.updateTimerDisplay(remaining / 1000);
                            Thread.sleep(1000);
                        }
                    } else {
                        // Break for 2 minutes
                        long startTime = System.currentTimeMillis();
                        while (isRunning && !isWorkTime) {
                            long elapsed = System.currentTimeMillis() - startTime;
                            long remaining = BREAK_TIME_MS - elapsed;
                            
                            if (remaining <= 0) {
                                // Break over, back to work
                                isWorkTime = true;
                                onWorkTime();
                                break;
                            }
                            
                            // Update GUI every second
                            gui.updateTimerDisplay(remaining / 1000);
                            Thread.sleep(1000);
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            gui.updateStatus("Stopped");
        });
        
        reminderThread.setDaemon(true);
        reminderThread.start();
    }
    
    /**
     * Called when it's time for a break
     */
    private void onBreakTime() {
        soundAlert.playBreakAlarm();
        gui.showBreakNotification();
        gui.updateStatus("⏸️ BREAK TIME! Rest for 2 minutes");
        System.out.println("[" + getCurrentTime() + "] Break time! Work for " + 
                         formatTime(BREAK_TIME_MS) + " minute(s)");
    }
    
    /**
     * Called when break is over and it's time to work again
     */
    private void onWorkTime() {
        soundAlert.playWorkAlarm();
        gui.showWorkNotification();
        gui.updateStatus("✓ Break over! Back to work (45 minutes)");
        System.out.println("[" + getCurrentTime() + "] Work session resumed! Work for " + 
                         formatTime(WORK_TIME_MS) + " minute(s)");
    }
    
    /**
     * Stop the reminder cycle
     */
    public void stop() {
        isRunning = false;
        gui.updateStatus("Stopped");
        System.out.println("[" + getCurrentTime() + "] Reminder stopped");
    }
    
    /**
     * Check if currently running
     */
    public boolean isRunning() {
        return isRunning;
    }
    
    /**
     * Check if currently in work time
     */
    public boolean isWorkTime() {
        return isWorkTime;
    }
    
    /**
     * Get current status string
     */
    public String getStatusString() {
        if (!isRunning) {
            return "Not running";
        }
        return isWorkTime ? "Work Session" : "Break Time";
    }
    
    /**
     * Format milliseconds to minutes:seconds
     */
    private String formatTime(long milliseconds) {
        long seconds = milliseconds / 1000;
        long minutes = seconds / 60;
        long secs = seconds % 60;
        return String.format("%d:%02d", minutes, secs);
    }
    
    /**
     * Get current time as string
     */
    private String getCurrentTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
