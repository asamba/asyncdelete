package pkg;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class DatabaseDeleteManager {
    public CompletableFuture<String> delete(String id) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("***DatabaseDeleteManager STARTING TO DELETE *** ==> : id = " + id);
                if("3".equals(id)){
                    throw new RuntimeException("Id 3 is issue");
                }
                Thread.sleep(1500);
                System.out.println("***DatabaseDeleteManager Deleted*** : id = " + id);
            } catch (Exception e) {
                System.out.println("********* =======>>>>>>>>>>>> Set the status of the database to previous so it can pick up again - ID: " + id);
                throw new RuntimeException(e);
            }
            return "successfully deleted " + id;
        }).exceptionally(e -> e.getMessage() + id);
    }

    private Function<Throwable, String> doSomething = exception -> exception.getMessage();
}
