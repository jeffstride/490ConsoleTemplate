package com.mrstride.services;

// Custom Log4J Appender for GUI Components
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.layout.PatternLayout;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.Color;

@Plugin(name = "SwingAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE)
public class SwingAppender extends AbstractAppender {
    
    private static JTextPane textPane;
    private static StyledDocument document;
    
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
    }
    
    @Override
    public void append(LogEvent event) {
        if (textPane == null || document == null) {
            return;
        }
        
        SwingUtilities.invokeLater(() -> {
            try {
                String message = new String(getLayout().toByteArray(event));
                String styleName = getStyleForLevel(event.getLevel().name());
                
                // Limit document size to prevent memory issues
                if (document.getLength() > 50000) {
                    document.remove(0, 10000);
                }
                
                document.insertString(document.getLength(), message, 
                                    textPane.getStyle(styleName));
                
                // Auto-scroll to bottom
                textPane.setCaretPosition(document.getLength());
                
            } catch (BadLocationException e) {
                // Handle silently or log to system error
                System.err.println("Error appending to GUI: " + e.getMessage());
            }
        });
    }
    
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