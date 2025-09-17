package com.mrstride.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * A proxy to the Log4j Loggers we want to use by Logger name.
 * 
 * We wrap in a class like this so that we can Mock it.
 * Do NOT annotate with @Service so that we can create the Mock
 * by registering the @Bean in the @TestConfiguration.
 */
@Service
public class LoggingFactory implements LoggingService {

    @Override
    public Logger getLogger(String name) {
        return name.equalsIgnoreCase("console") ? LogManager.getRootLogger() : LogManager.getLogger(name);
    }
    
}
