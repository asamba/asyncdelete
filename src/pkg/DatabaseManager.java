package pkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class DatabaseManager {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test";
    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";

    static final int CONCURRENT_PROCESSING_LIMIT = 2;

    DatabaseDeleteManager databaseDeleteManager = new DatabaseDeleteManager();

    public void deleteIds() throws  Exception{

        long startTime = System.currentTimeMillis();

        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM TEST");

            List<String> deletableIds = new ArrayList<>(CONCURRENT_PROCESSING_LIMIT);

            int currentParallelCount = 0;
            while (rs.next()) {
                String id = Integer.toString(rs.getInt("ID"));
                String name = rs.getString("NAME"); // Assuming there is a column called name.

                if(currentParallelCount < CONCURRENT_PROCESSING_LIMIT) {
                    deletableIds.add(id);
                    currentParallelCount++;
                }
                if(currentParallelCount == CONCURRENT_PROCESSING_LIMIT || rs.isLast()) {
                    currentParallelCount = 0;
                    executeDeleteConcurrently(deletableIds);
                    deletableIds = new ArrayList<>(CONCURRENT_PROCESSING_LIMIT);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Duration: " + (endTime - startTime)/1000 + " secs");
    }

    private void executeDeleteConcurrently(List<String> deletableIds) throws  Exception{

        List<CompletableFuture<String>> completableFutureList = new ArrayList<>();
        ExecutorService deleteParallelizedExecutor = Executors.newFixedThreadPool(CONCURRENT_PROCESSING_LIMIT);

        for (String id : deletableIds) {

            CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> databaseDeleteManager.delete(id), deleteParallelizedExecutor);
            completableFutureList.add(completableFuture);
        }
        //wait for all completion
        CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]))
                .exceptionally(ex -> {
                    System.out.println("Did not delete id" + ex.getMessage());
                    return  null;
                })
                .join();

        //Map<Boolean, List<CompletableFuture<String>>> collect = completableFutureList.stream().collect(Collectors.partitioningBy(CompletableFuture::isCompletedExceptionally));
        //print result
        System.out.println("******************** print result ********************" + completableFutureList.size());
        List<CompletableFuture<String>> collect = completableFutureList.stream().collect(Collectors.toList());
        for (CompletableFuture<String> item : collect) {
            String deletedId = item.get();
            System.out.println("result = " + deletedId);
        }
    }

}
