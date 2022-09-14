package pkg;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DatabaseDeleteManager {

    public CompletableFuture<String> delete(String id) {

        ExecutorService yourOwnExecutor = Executors.newFixedThreadPool(2);

        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("Deleting : id = " + id);
                Thread.sleep(10000);
                System.out.println("***Deleted*** : id = " + id);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return "RETURN ----- Deleted: " + id;
        }, yourOwnExecutor);
    }

}
