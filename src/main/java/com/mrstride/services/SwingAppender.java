package com.mrstride.services;

// Custom Log4J Appender for GUI Components
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.Level;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.Color;
import java.io.Serializable;

@Plugin(name = "SwingAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE)
public class SwingAppender extends AbstractAppender {
    
    private static JTextPane textPane;
    private static StyledDocument document;

    public static boolean useThreadColors = true;
    
    protected SwingAppender(String name, Filter filter, Layout<? extends Serializable> layout) {
        super(name, filter, layout, true, null);
    }
    
    @PluginFactory
    public static SwingAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Filter") Filter filter,
            @PluginElement("Layout") Layout<? extends Serializable> layout) {
        
        if (name == null) {
            LOGGER.error("No name provided for SwingAppender");
            return null;
        }
        
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        
        return new SwingAppender(name, filter, layout);
    }
    
    // Static method to set the GUI component
    public static void setTextPane(JTextPane textPane) {
        SwingAppender.textPane = textPane;
        SwingAppender.document = textPane.getStyledDocument();
        setupStyles();
    }
    
    private static void setupStyles() {
        if (document == null) return;
        
        Style defaultStyle = textPane.addStyle("default", null);
        StyleConstants.setForeground(defaultStyle, Color.BLACK);
        
        Style errorStyle = textPane.addStyle("error", defaultStyle);
        StyleConstants.setForeground(errorStyle, Color.RED);
        StyleConstants.setBold(errorStyle, true);
        
        Style warnStyle = textPane.addStyle("warn", defaultStyle);
        StyleConstants.setForeground(warnStyle, Color.ORANGE);
        
        Style infoStyle = textPane.addStyle("info", defaultStyle);
        StyleConstants.setForeground(infoStyle, Color.BLUE);
        
        Style debugStyle = textPane.addStyle("debug", defaultStyle);
        StyleConstants.setForeground(debugStyle, Color.GRAY);

        setupThreadStyles(10);
    }

    private static void setupThreadStyles(int n) {
        
        for (int iThread = 0; iThread < n; iThread++) {
            String threadName = "Thread-" + iThread;

            Style threadStyle = textPane.addStyle("info:" + threadName, null);
            StyleConstants.setForeground(threadStyle, PALETTE[iThread]);               

            Style warnStyle = textPane.addStyle("warn:" + threadName, threadStyle);
            StyleConstants.setBold(warnStyle, true);

            Style errorStyle = textPane.addStyle("error:" + threadName, warnStyle);
            StyleConstants.setUnderline(errorStyle, true);
            
            Style debugStyle = textPane.addStyle("debug:" + threadName, threadStyle);
            StyleConstants.setItalic(debugStyle, true);
        }
    }

    private static int getThreadNum(String s) {
        // input is of type "Thread-19"
        // Find the last digits to return 9

        // move past non-digits
        int i = 0;
        while (i < s.length() && !Character.isDigit(s.charAt(i))) {
            i++;
        }
        // not good, assume thread 0
        if (i == s.length()) return 0;

        // get the last digit
        int n = 0;
        while (i < s.length() && Character.isDigit(s.charAt(i))) {
            n = Character.getNumericValue(s.charAt(i));
            i++;
        }
        return n;
    }

    private static String getThreadStyleName(String s, Level level) {
        int n = getThreadNum(s);
        String result = level.name().toLowerCase() + ":Thread-" + n;

        return result;
    }
    
    @Override
    public void append(LogEvent event) {
        if (textPane == null || document == null) {
            return;
        }
        
        final String message = new String(getLayout().toByteArray(event));
        final String styleName = !useThreadColors ? getStyleForLevel(event.getLevel().name()) :
            getThreadStyleName(event.getThreadName(), event.getLevel());

        SwingUtilities.invokeLater(() -> {
            try {
                // Limit document size to prevent memory issues
                if (document.getLength() > 50000) {
                    document.remove(0, 10000);
                }
                
                document.insertString(document.getLength(), message, textPane.getStyle(styleName));
                
                // Auto-scroll to bottom
                textPane.setCaretPosition(document.getLength());
                
            } catch (BadLocationException e) {
                // Handle silently or log to system error
                System.err.println("Error appending to GUI: " + e.getMessage());
            }
        });
    }

    private static final Color[] PALETTE = new Color[] {
        new Color(0x000000), // Black
        new Color(0x0B3D91), // Royal Blue (dark)
        new Color(0x1B5E20), // Evergreen (dark green)
        new Color(0x4A148C), // Aubergine (deep purple)
        new Color(0xB71C1C), // Crimson (dark red)
        new Color(0x6D4C41), // Brown
        new Color(0x37474F), // Blue Grey
        new Color(0x004D40), // Deep Teal
        new Color(0x3E2723), // Chocolate
        new Color(0x880E4F)  // Deep Magenta
    };
 
    private String getStyleForLevel(String level) {
        switch (level.toLowerCase()) {
            case "error":
            case "fatal":
                return "error";
            case "warn":
                return "warn";
            case "info":
                return "info";
            case "debug":
            case "trace":
                return "debug";
            default:
                return "default";
        }
    }
}