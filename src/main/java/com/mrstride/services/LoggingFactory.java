package com.mrstride.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;


/**
 * A proxy to the Log4j Loggers we want to use by Logger name.
 * 
 * We wrap in a class like this so that we can Mock it.
 * Do NOT annotate with @Service so that we can create the Mock
 * by registering the @Bean in the @TestConfiguration.
 * 
 * TODO:
 * 
 * Actually, consider upgrading to Junit 5.4.
 * <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.0</version>
    <scope>test</scope>
</dependency>

 * Then... 
@ExtendWith(MockitoExtension.class)
class MyServiceTest {
    
    @Mock
    private MyRepository myRepository;  // Mock dependencies
    
    @InjectMocks
    private MyService myService;  // The actual service under test
    
    @Test
    void shouldProcessBusinessLogic() {
        // Given
        when(myRepository.findById(1L)).thenReturn(Optional.of(new Entity()));
        
        // When
        String result = myService.processData("input");
        
        // Then
        assertEquals("expected", result);
    }
}
}
 */
@Service
public class LoggingFactory implements LoggingService {

    @Override
    public Logger getLogger(String name) {
        return name.equalsIgnoreCase("console") ? LogManager.getRootLogger() : LogManager.getLogger(name);
    }
    
}
