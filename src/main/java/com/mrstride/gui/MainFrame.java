package com.mrstride.gui;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.mrstride.console.SortingWork;
import com.mrstride.services.SwingAppender;

import javax.swing.*;
import java.awt.*;

// This class starts all the threads and creates all the panels. It also creates the menu options.
public class MainFrame extends JFrame {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;

    public static MainFrame theFrame = null;

    private JTextPane loggingPane;
    private JScrollPane scrollPane;
    private JPanel progressExample;

    public static Logger consolePane = LogManager.getLogger("swing");

    public MainFrame() {
        theFrame = this;
    }

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
        
        scrollPane = new JScrollPane(loggingPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setVisible(true);
        this.add(scrollPane);

        // JFrame must be set to visible 
        this.setVisible(true);

        progressExample = new ProgressExample();

        System.out.println("All done creating our frame");

        // setup the logging to the ScrollPane
        SwingAppender.setTextPane(loggingPane);
        consolePane.info("Application started - logging to ScrollPane enabled");
        consolePane.info("Info Style");
        consolePane.error("Error Style");
        consolePane.warn("Warning Style");
        consolePane.debug("Debug Style");
    }

    public void showProgress() {
        this.remove(scrollPane);
        scrollPane.setVisible(false);

        this.add(progressExample);
        progressExample.setVisible(true);

        revalidate();
        repaint();
    }

    public void showConsole() {
        this.remove(progressExample);
        progressExample.setVisible(false);

        this.add(scrollPane);
        scrollPane.setVisible(true);

        revalidate();
        repaint();
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
        JMenu menu = new JMenu("Demo");
        menu.setMnemonic('D');

        JMenuItem item = new JMenuItem("Progress bar");
        item.addActionListener(ae -> showProgress());
        menu.add(item);

        item = new JMenuItem("Background Task");
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
