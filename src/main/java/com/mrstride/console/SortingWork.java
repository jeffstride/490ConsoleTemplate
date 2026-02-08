package com.mrstride.console;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class SortingWork  {

    public static Logger consolePane = LogManager.getLogger("swing");

    public static void start() {
        consolePane.info("Info message");
        consolePane.warn("Warning message");
        consolePane.error("Error message");
    }
}

