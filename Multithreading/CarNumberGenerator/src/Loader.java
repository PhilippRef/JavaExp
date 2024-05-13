import java.util.concurrent.*;

public class Loader {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        final int COUNT_THREADS = Runtime.getRuntime().availableProcessors();
        final int countRegions = 99;
        final int regionsOneThread = countRegions / COUNT_THREADS;

        ExecutorService service = Executors.newFixedThreadPool(COUNT_THREADS);

        for (int i = 1; i <= COUNT_THREADS; i++) {
            int fromReg = regionsOneThread * (i - 1) + 1;
            int toReg = fromReg + regionsOneThread;

            if (i == COUNT_THREADS) {
                toReg = countRegions + 1;
            }

            NumberGenerator numberGenerator = new NumberGenerator(fromReg, toReg, i, start);
            service.execute(numberGenerator);
        }
        service.shutdown();
        try {
            if (!service.awaitTermination(60, TimeUnit.SECONDS)) {
                service.shutdownNow();
            }
        } catch (InterruptedException ex) {
            service.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("Main Thread finish: " + (System.currentTimeMillis() - start) + " ms");
    }
}
