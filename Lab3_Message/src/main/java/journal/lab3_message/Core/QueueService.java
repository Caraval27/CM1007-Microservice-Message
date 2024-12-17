package journal.lab3_message.Core;

import org.springframework.stereotype.Service;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

@Service
public class QueueService {
    private final BlockingQueue<String> gpResponseQueue = new ArrayBlockingQueue<>(1);

    public void putResponse(String response) throws InterruptedException {
        gpResponseQueue.put(response);
    }

    public String takeResponse() throws InterruptedException {
        return gpResponseQueue.take();
    }
}
