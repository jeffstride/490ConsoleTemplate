package com.mrstride.gui;

import org.apache.logging.log4j.Logger;

import com.mrstride.ApplicationContextProvider;
import com.mrstride.console.SortingWork;
import com.mrstride.services.LoggingService;
import com.mrstride.services.SwingAppender;

import javax.swing.*;
import java.awt.*;

// This class starts all the threads and creates all the panels. It also creates the menu options.
public class MainFrame extends JFrame {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;

    public static MainFrame theFrame = null;

    public static Logger consoleLogger = ApplicationContextProvider
        .getApplicationContext()
        .getBean(LoggingService.class)
        .getLogger("swing");

    private JTextPane loggingPane;

    /**
     * Create the main JFrame and all animation JPanels.
     */
    public void createFrame() {
        // Use the default LayoutManager (BorderLayout)
        // No need to call this.setLayout(...);
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("490 Console Work");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addMenuBar();

        loggingPane = new JTextPane();
        loggingPane.setEditable(false);
        loggingPane.setBackground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(loggingPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        // Review: why these dimensions?
        scrollPane.setPreferredSize(new Dimension(780, 600));
        scrollPane.setVisible(true);
        this.add(scrollPane);

        // JFrame must be set to visible 
        this.setVisible(true);

        System.out.println("All done creating our frame");

        // setup the logging to the ScrollPane
        SwingAppender.setTextPane(loggingPane);
        consoleLogger.info("Application started - logging to ScrollPane enabled");
        consoleLogger.info("Info Color");
        consoleLogger.error("Error Color");
        consoleLogger.warn("Warning level");
        consoleLogger.debug("Debug Color");
    }

    /**
     * Add some menu options to control the experience.
     */
    private void addMenuBar() {

        JMenuBar bar = new JMenuBar();
        this.setJMenuBar(bar);

        JMenu menu = createMainMenu();
        bar.add(menu);

        menu = createConsoleMenu();
        bar.add(menu);
    }

    /**
     * Create the top-level menu for Options
     * 
     * @return The JMenu object with all the JMenuItems in it.
     */
    private JMenu createMainMenu() {
        JMenu menu = new JMenu("Options");
        menu.setMnemonic('O');

        JMenuItem item = new JMenuItem("Placeholder");
        menu.add(item);
        
        return menu;
    }
    /**
     * Create the top-level menu for Console work
     * 
     * @return The JMenu object with all the JMenuItems in it.
     */
    private JMenu createConsoleMenu() {
        JMenu menu = new JMenu("Console");
        menu.setMnemonic('C');

        // create menu items
        JMenuItem item = new JMenuItem("Sorting");
        item.addActionListener(ae -> SortingWork.start());
        menu.add(item);
        
        return menu;
    }
}
