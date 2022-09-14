package pkg;

public class TestMain {

    public static void main(String[] args) throws  Exception{


        DatabaseManager databaseManager = new DatabaseManager();
        databaseManager.fetchIds();

        //long startTime = System.currentTimeMillis();

//        ResultSetGenerator resultSetGenerator = new ResultSetGenerator();
//        List<String> idsToDelete = resultSetGenerator.generate();
//        DatabaseDeleteManager databaseDeleteManager = new DatabaseDeleteManager();
//        List<CompletableFuture<String>> completableFutureList = new ArrayList<>();
//
//
//        for (String id: idsToDelete) {
//            CompletableFuture<String> completableFuture = databaseDeleteManager.delete(id);
//            completableFutureList.add(completableFuture);
//        }
//
//        //wait for all completion
//        CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[0]))
//                .exceptionally(ex -> null)
//                .join();
//
//        //Map<Boolean, List<CompletableFuture<String>>> collect = completableFutureList.stream().collect(Collectors.partitioningBy(CompletableFuture::isCompletedExceptionally));
//        //print result
//        List<CompletableFuture<String>> collect = completableFutureList.stream().collect(Collectors.toList());
//        for (CompletableFuture<String> item : collect) {
//            String deletedId = item.get();
//            System.out.println("result = " + deletedId);
//        }

        //long endTime = System.currentTimeMillis();

        //System.out.println("Duration: " + (endTime - startTime)/1000 + " secs");

    }
}
