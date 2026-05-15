package com.breakreminder;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;

/**
 * Handles sound alerts for break and work time notifications.
 */
public class SoundAlert {
    
    /**
     * Play alarm sound for break time
     */
    public void playBreakAlarm() {
        playTone(800, 500); // 800 Hz, 500ms
        try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        playTone(800, 500);
    }
    
    /**
     * Play alarm sound for work time resumption
     */
    public void playWorkAlarm() {
        playTone(1000, 300); // 1000 Hz, 300ms
        try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        playTone(1000, 300);
        try { Thread.sleep(100); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        playTone(1000, 300);
    }
    
    /**
     * Play a tone with specified frequency and duration
     */
    private void playTone(int frequency, int duration) {
        try {
            int sampleRate = 44100;
            
            // Generate sine wave
            byte[] sample = new byte[sampleRate * duration / 1000];
            for (int i = 0; i < sample.length; i++) {
                double angle = 2.0 * Math.PI * i * frequency / sampleRate;
                sample[i] = (byte) (Math.sin(angle) * 100);
            }
            
            // Create audio input stream
            AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, false);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(sample);
            AudioInputStream audioInputStream = new AudioInputStream(inputStream, format, sample.length);
            
            // Get audio clip and play
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            
            // Wait for sound to finish
            Thread.sleep(duration + 50);
        } catch (Exception e) {
            System.err.println("Error playing sound: " + e.getMessage());
        }
    }
}
