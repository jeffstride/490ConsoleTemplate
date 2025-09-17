package com.mrstride.gui;

// Move this to MainFrame.java
import com.mrstride.services.SwingAppender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.swing.*;
import java.awt.*;

public class LoggingGUIExample extends JFrame {
    private static final Logger logger = LogManager.getLogger(LoggingGUIExample.class);
    private JTextPane logTextPane;
    
    public LoggingGUIExample() {
        initializeGUI();
        setupLogging();
    }
    
    private void initializeGUI() {
        setTitle("Application with Logging Console");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        
        // Create the log display area
        logTextPane = new JTextPane();
        logTextPane.setEditable(false);
        logTextPane.setBackground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(logTextPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setPreferredSize(new Dimension(780, 200));
        
        // Main content area
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Your application content goes here
        JPanel contentPanel = new JPanel();
        contentPanel.add(new JLabel("Your application content here"));
        
        // Test buttons
        JPanel buttonPanel = new JPanel();
        JButton infoButton = new JButton("Log Info");
        JButton warnButton = new JButton("Log Warning");
        JButton errorButton = new JButton("Log Error");
        
        infoButton.addActionListener(e -> logger.info("This is an info message"));
        warnButton.addActionListener(e -> logger.warn("This is a warning message"));
        errorButton.addActionListener(e -> logger.error("This is an error message"));
        
        buttonPanel.add(infoButton);
        buttonPanel.add(warnButton);
        buttonPanel.add(errorButton);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.NORTH);
        mainPanel.add(new JLabel("Application Log:"), BorderLayout.SOUTH);
        
        // Split pane to separate main content from log
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, mainPanel, scrollPane);
        splitPane.setDividerLocation(350);
        splitPane.setResizeWeight(0.7);
        
        add(splitPane);
    }
    
    private void setupLogging() {
        // Set the text pane for the custom appender
        SwingAppender.setTextPane(logTextPane);
        
        // Log startup message
        logger.info("Application started - logging to GUI enabled");
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoggingGUIExample().setVisible(true);
        });
    }
}