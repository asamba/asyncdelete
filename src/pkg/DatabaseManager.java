package pkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class DatabaseManager {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test";
    //  Database credentials
    static final String USER = "sa";
    static final String PASS = "";

     List<CompletableFuture<String>> completableFutureList = new ArrayList<>();

    public void fetchIds() throws  Exception{

        long startTime = System.currentTimeMillis();

        Connection conn = null;
        Statement stmt = null;
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM TEST");

            while (rs.next()) {
                String id = Integer.toString(rs.getInt("ID"));
                String name = rs.getString("NAME"); // Assuming there is a column called name.
                //System.out.println("DB Output id: " + id + " Name: " + name);

                ResultSetGenerator resultSetGenerator = new ResultSetGenerator();
                List<String> idsToDelete = resultSetGenerator.generate();
                DatabaseDeleteManager databaseDeleteManager = new DatabaseDeleteManager();

                CompletableFuture<String> completableFuture = databaseDeleteManager.delete(id);
                System.out.println("Adding the completableFutureList " + id);
                boolean added = completableFutureList.add(completableFuture);
                System.out.println("added = " + added + " for id " + id);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("******************** Before CompletableFuture ********************");
        //wait for all completion
        CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]))
                .exceptionally(ex -> null)
                .join();

        //Map<Boolean, List<CompletableFuture<String>>> collect = completableFutureList.stream().collect(Collectors.partitioningBy(CompletableFuture::isCompletedExceptionally));
        //print result
        System.out.println("******************** print result ********************" + completableFutureList.size());
        List<CompletableFuture<String>> collect = completableFutureList.stream().collect(Collectors.toList());
        for (CompletableFuture<String> item : collect) {
            String deletedId = item.get();
            System.out.println("result = " + deletedId);
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Duration: " + (endTime - startTime)/1000 + " secs");


    }
}
