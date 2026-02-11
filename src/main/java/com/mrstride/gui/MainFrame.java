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

    private static final String CARD_CONSOLE = "console";
    private static final String CARD_PROGRESS = "progress";

    // Container with CardLayout
    private JPanel cards;             
    private JScrollPane consoleCard; 
    private JPanel progressCard; 

    public static Logger consolePane = LogManager.getLogger("swing");

    public MainFrame() {
        theFrame = this;
    }

    /**
     * Create the main JFrame and all animation JPanels.
     */
    public void createFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("490 Console Work");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addMenuBar();

        JTextPane loggingPane = new JTextPane();
        loggingPane.setEditable(false);
        loggingPane.setBackground(Color.WHITE);    
        consoleCard = new JScrollPane(loggingPane);
        consoleCard.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        progressCard = new ProgressExample(this::showConsole);

        // Host with CardLayout to make switching between panels easy
        cards = new JPanel(new CardLayout());
        cards.add(consoleCard, CARD_CONSOLE);
        cards.add(progressCard, CARD_PROGRESS);

        // Put cards into the frame (frame stays BorderLayout)
        this.add(cards, BorderLayout.CENTER);

        // JFrame must be set to visible 
        this.setVisible(true);

        // setup the logging to the GUI console
        SwingAppender.setTextPane(loggingPane);
        consolePane.info("Application started - logging to ScrollPane enabled");
        consolePane.info("Info Style");
        consolePane.error("Error Style");
        consolePane.warn("Warning Style");
        consolePane.debug("Debug Style");
    }

    public void showProgress() {
        ((CardLayout) cards.getLayout()).show(cards, CARD_PROGRESS);
    }

    public void showConsole() {
        ((CardLayout) cards.getLayout()).show(cards, CARD_CONSOLE);
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
