package util.logger;

public class Main {

    public static void main(String[] args) {

        CustomLogger customLogger = new CustomLogger("log/app.log", LogLevel.INFO);

        customLogger.info("OKKKAY");

        customLogger.close();

    }
}
