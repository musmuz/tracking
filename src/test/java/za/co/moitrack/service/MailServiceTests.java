package za.co.moitrack.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import za.co.moitrack.DemoApplicationTests;

public class MailServiceTests extends DemoApplicationTests {
    private Log log = LogFactory.getLog(this.getClass().getName());
    @Autowired
    private MailService mailService;


    @Test
    public void sendNumberOfReportingUnitsTest() {
        log.debug("Will test in here for sending number of units reporting");
    }
}
