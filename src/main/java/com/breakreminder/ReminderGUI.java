package com.breakreminder;

import javax.swing.*;
import java.awt.*;

/**
 * GUI for the Break Reminder application.
 * Shows current status, timer, and provides start/stop controls.
 */
public class ReminderGUI extends JFrame {
    private JLabel timerLabel;
    private JLabel statusLabel;
    private JButton startButton;
    private JButton stopButton;
    private BreakReminder breakReminder;
    
    public ReminderGUI(BreakReminder breakReminder) {
        this.breakReminder = breakReminder;
        initializeUI();
    }
    
    /**
     * Initialize the user interface
     */
    private void initializeUI() {
        setTitle("Work Break Reminder");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Title
        JLabel titleLabel = new JLabel("Work Break Reminder");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Status label
        statusLabel = new JLabel("Ready to start");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(statusLabel);
        mainPanel.add(Box.createVerticalStrut(15));
        
        // Timer display
        timerLabel = new JLabel("45:00");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 48));
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timerLabel.setForeground(new Color(0, 102, 204));
        mainPanel.add(timerLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(new Color(240, 240, 240));
        
        // Start button
        startButton = new JButton("Start");
        startButton.setPreferredSize(new Dimension(100, 40));
        startButton.setFont(new Font("Arial", Font.PLAIN, 12));
        startButton.addActionListener(e -> breakReminder.start());
        buttonPanel.add(startButton);
        
        // Stop button
        stopButton = new JButton("Stop");
        stopButton.setPreferredSize(new Dimension(100, 40));
        stopButton.setFont(new Font("Arial", Font.PLAIN, 12));
        stopButton.setEnabled(false);
        stopButton.addActionListener(e -> {
            breakReminder.stop();
            updateButtonStates();
            timerLabel.setText("45:00");
        });
        buttonPanel.add(stopButton);
        
        mainPanel.add(buttonPanel);
        
        setContentPane(mainPanel);
        setVisible(true);
    }
    
    /**
     * Update the timer display with remaining time in seconds
     */
    public void updateTimerDisplay(long secondsRemaining) {
        SwingUtilities.invokeLater(() -> {
            long minutes = secondsRemaining / 60;
            long seconds = secondsRemaining % 60;
            String timeString = String.format("%d:%02d", minutes, seconds);
            timerLabel.setText(timeString);
        });
    }
    
    /**
     * Update the status label
     */
    public void updateStatus(String status) {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText(status);
            updateButtonStates();
        });
    }
    
    /**
     * Show a notification when break time starts
     */
    public void showBreakNotification() {
        SwingUtilities.invokeLater(() -> {
            timerLabel.setForeground(new Color(255, 102, 0)); // Orange for break
            JOptionPane.showMessageDialog(
                this,
                "⏸️ BREAK TIME!\n\nTake a 2-minute break.",
                "Break Notification",
                JOptionPane.INFORMATION_MESSAGE
            );
            timerLabel.setText("02:00");
        });
    }
    
    /**
     * Show a notification when work time resumes
     */
    public void showWorkNotification() {
        SwingUtilities.invokeLater(() -> {
            timerLabel.setForeground(new Color(0, 102, 204)); // Blue for work
            JOptionPane.showMessageDialog(
                this,
                "✓ Break Over!\n\nBack to work for 45 minutes.",
                "Work Notification",
                JOptionPane.INFORMATION_MESSAGE
            );
            timerLabel.setText("45:00");
        });
    }
    
    /**
     * Update button enabled/disabled states
     */
    private void updateButtonStates() {
        startButton.setEnabled(!breakReminder.isRunning());
        stopButton.setEnabled(breakReminder.isRunning());
    }
}
