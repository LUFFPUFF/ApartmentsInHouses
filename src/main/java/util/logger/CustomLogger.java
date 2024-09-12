package util.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class CustomLogger {

    private PrintWriter writer;
    private LogLevel level;

    public CustomLogger(String fileName, LogLevel level) {
        try {
            this.writer = new PrintWriter(new FileWriter(fileName, true));
            this.level = level;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void log(LogLevel level, String message) {
        if (this.level.ordinal() == level.ordinal()) {
            String timestamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());
            writer.println("[" + timestamp + "]" + "[" + level + "] " + message);
        }
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public void warning(String message) {
        log(LogLevel.WARN, message);
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void close() {
        if (writer != null) {
            writer.close();
        }
    }
}
