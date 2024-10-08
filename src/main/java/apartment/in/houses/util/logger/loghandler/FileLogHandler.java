package apartment.in.houses.util.logger.loghandler;

import apartment.in.houses.util.logger.exception.LogHandlerException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileLogHandler implements LogHandler {

    private final String filePath;

    public FileLogHandler(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void log(String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
