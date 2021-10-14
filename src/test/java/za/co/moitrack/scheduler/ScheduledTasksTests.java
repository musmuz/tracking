package za.co.moitrack.scheduler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import za.co.moitrack.DemoApplicationTests;

public class ScheduledTasksTests extends DemoApplicationTests {
    private Log log = LogFactory.getLog(this.getClass().getName());

    @Autowired
    private ScheduledTasks scheduledTasks;


//    @Test
//    public void reportCurrentTimeTest() {
//        scheduledTasks.reportCurrentTime();
//   }
}
