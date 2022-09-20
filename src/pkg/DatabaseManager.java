package pkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DatabaseManager {

    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:~/test";
    static final String USER = "sa";
    static final String PASS = "";
    static final int CONCURRENT_PROCESSING_LIMIT = 1;
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
                    //executeDeleteConcurrently(deletableIds);
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

    private void executeDeleteConcurrently(List<String> deletableIds) {
        CompletableFuture[] futures = deletableIds.stream().map(databaseDeleteManager::delete).toArray(CompletableFuture[]::new);
        CompletableFuture
                .allOf(futures)
                .join();
    }
}