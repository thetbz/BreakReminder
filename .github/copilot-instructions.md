# BreakReminder - Project Configuration

## Project Overview
Java Work Break Reminder application that automatically triggers 2-minute break notifications every 45 minutes of work.

## Features
- Automatic work/break cycles (45 min work, 2 min break)
- GUI notification system
- Sound alerts
- System tray integration
- No manual intervention required

## Build and Run
- Build: `mvn clean package`
- Run: `java -jar target/BreakReminder.jar`
- Or: `mvn exec:java -Dexec.mainClass="com.breakreminder.Main"`

## Project Structure
```
BreakReminder/
├── src/main/java/com/breakreminder/
│   ├── Main.java
│   ├── BreakReminder.java
│   ├── ReminderGUI.java
│   └── SoundAlert.java
├── pom.xml
└── README.md
```
