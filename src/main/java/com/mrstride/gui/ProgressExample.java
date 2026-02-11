package com.mrstride.gui;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * The ProgressExample is an AnimationPanel so that our MainFrame
 * will call "start" where we can trigger the ProgressBar.
 */
public class ProgressExample extends JPanel {

    public ProgressExample() {
        // we create the components for the JPanel here.
        // This must be done on the EDT Thread. Since MainFrame is created on the EDT,
        // then this will also be constructed on the EDT.
        JButton button = new JButton("Return");
        button.addActionListener(ae -> MainFrame.theFrame.showConsole());
        this.add(button);

        button.setPreferredSize(new Dimension(100, 20));
    }


}

/*
 * 
 * The notify() (and notifyAll()) method must be called from within a
 * synchronized block or method because it ensures that the thread calling
 * notify() holds the monitor lock on the object being notified. This is crucial
 * for maintaining the integrity of the synchronization mechanism.
 * 
 * Here's why it's necessary:
 * 
 * Consistency and Safety: When a thread calls wait(), it releases the monitor
 * lock and enters the waiting state. To ensure that the state of the shared
 * resource is consistent and safe when a thread is notified, the notify()
 * method must be called while holding the monitor lock. This prevents race
 * conditions and ensures that the notified thread can safely re-acquire the
 * lock and proceed.
 * 
 * Monitor Ownership: The Java synchronization mechanism is based on the concept
 * of monitor ownership. Only the thread that holds the monitor lock can call
 * wait(), notify(), or notifyAll(). This ensures that the thread has exclusive
 * access to the shared resource and can safely modify its state.
 * 
 * Avoiding IllegalMonitorStateException: If notify() or notifyAll() is called
 * outside of a synchronized block or method, the JVM will throw an
 * IllegalMonitorStateException. This exception indicates that the thread does
 * not hold the monitor lock on the object, which is a violation of the
 * synchronization contract.
 */
