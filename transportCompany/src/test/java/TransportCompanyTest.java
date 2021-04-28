import org.junit.jupiter.api.Test;
import ru.ccfit.nsu.kokunina.TransportCompany;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import static java.lang.Thread.sleep;


public class TransportCompanyTest {

    @Test
    void checkThatWorkStopsProperly() throws InterruptedException {
        int WORK_TIME_MS = 20000;

        final TransportCompany transportCompany = new TransportCompany();
        int currThreadsCount = Thread.getAllStackTraces().keySet().size();
        transportCompany.startWork();
        sleep(WORK_TIME_MS);
        transportCompany.stopWork();
        Set<Thread> threads = Thread.getAllStackTraces().keySet();
        assertEquals(currThreadsCount, threads.size());
    }
}
