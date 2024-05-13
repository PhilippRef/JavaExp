import java.io.*;

public class NumberGenerator implements Runnable {
    private final char[] letters = {'У', 'К', 'Е', 'Н', 'Х', 'В', 'А', 'Р', 'О', 'С', 'М', 'Т'};
    private PrintWriter writer;
    private final long startThread;
    private final int startRegion;
    private final int endRegion;
    private final int fileNumber;

    public NumberGenerator(int startRegion, int endRegion, int fileNumber, long startThread) {
        this.startRegion = startRegion;
        this.endRegion = endRegion;
        this.fileNumber = fileNumber;
        this.startThread = startThread;
    }

    @Override
    public void run() {

        try {
            writer = new PrintWriter("res/numbers_" + fileNumber + ".txt");
        } catch (FileNotFoundException e) {
            System.out.println("write error");
        }

        for (int regionCode = startRegion; regionCode < endRegion; regionCode++) {
            StringBuilder sb = new StringBuilder();
            for (int number = 1; number < 1000; number++) {
                for (char firstLetter : letters) {
                    for (char secondLetter : letters) {
                        for (char thirdLetter : letters) {
                            sb.append(firstLetter);
                            sb.append(padNumber(number, 3));
                            sb.append(secondLetter);
                            sb.append(thirdLetter);
                            sb.append(padNumber(regionCode, 2));
                            sb.append("\n");
                        }
                    }
                }
            }
            writer.write(sb.toString());
        }
        writer.flush();
        writer.close();
        System.out.println("Threads finish: " + (System.currentTimeMillis() - startThread) + "ms");
    }

    private StringBuilder padNumber(int number, int numberLength) {
        StringBuilder numberStr = new StringBuilder(Integer.toString(number));
        int padSize = numberLength - numberStr.length();
        for (int i = 0; i < padSize; i++) {
            numberStr.insert(0, '0');
        }
        return numberStr;
    }
}


