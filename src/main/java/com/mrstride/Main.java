package com.mrstride;

import java.io.File;

import javax.swing.SwingUtilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mrstride.gui.MainFrame;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class Main implements CommandLineRunner {

    /**
     * This demonstrates Field Injection as a style of Dependency Injection.
     * It is highly recommended to use Constructor Injection instead.
     */
    @Autowired
    Actor1 behave;
    
    public static void main( String[] args ) {
        deleteLogs();

        // BUG: my first run said that I was headless, but with this print, it says false. Odd.
        // System.out.println("Before: Headless mode: " + java.awt.GraphicsEnvironment.isHeadless());
        // Alternatively, I can set the headless to be false and all is good.
        System.setProperty("java.awt.headless", "false");
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // Invoke the UI Thread to create the Main frame. 
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.createFrame();
        });

        System.out.println("Hello World");
        
        behave.act();
    }
   
    private static void deleteLogs() {
        
        // I can't seem to get the Log4j to automatically delete the file, so do it manually.
        // Delete the log file if it exists
        File logDir = new File("logs");
        if (!logDir.exists()) {
            logDir.mkdirs();
        }
        System.out.println(logDir.getAbsolutePath());
        File[] files = logDir.listFiles((dir, name) -> name.endsWith(".log"));
        for (File logFile : files) {
            if (logFile.exists()) {
                if (logFile.delete()) {
                    System.out.printf("Log file (%s) deleted successfully\n.", logFile.getName());
                } else {
                    System.out.printf("Failed to delete the log file: %s.\n", logFile.getAbsolutePath());
                }
            }
        }
    }
}
