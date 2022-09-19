package pkg;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseDeleteManager {
    public String delete(String id) {
        try {
            System.out.println("***DatabaseDeleteManager STARTING TO DELETE *** : id = " + id);
            Thread.sleep(3000);
            System.out.println("***DatabaseDeleteManager Deleted*** : id = " + id);
        } catch (InterruptedException e) {
            System.out.println("Could not delete Id " + id);
            throw new RuntimeException(e);
        }

        return "successfuly deleted " + id;
    }
}
