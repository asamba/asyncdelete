package pkg;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

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
    public CompletableFuture<String> delete2(String id) {
        try {
            System.out.println("***DatabaseDeleteManager STARTING TO DELETE *** : id = " + id);
            Thread.sleep(3000);
            System.out.println("***DatabaseDeleteManager Deleted*** : id = " + id);
        } catch (InterruptedException e) {
            System.out.println("Could not delete Id " + id);
            throw new RuntimeException(e);
        }

        return CompletableFuture.completedFuture("successfuly deleted " + id).exceptionally(doSomething);
    }

    private Function<Throwable, String> doSomething = exception -> exception.getMessage();
}
