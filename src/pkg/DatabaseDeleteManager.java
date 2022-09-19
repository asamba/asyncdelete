package pkg;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseDeleteManager {
    public String delete(String id) {

        String success = "FALSE";

        try {
            System.out.println("***DatabaseDeleteManager STARTING TO DELETE *** : id = " + id);
            Thread.sleep(3000);
            System.out.println("***DatabaseDeleteManager Deleted*** : id = " + id);
            success = "TRUE";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return success;
    }
}
