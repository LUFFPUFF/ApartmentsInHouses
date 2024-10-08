package apartment.in.houses.util.logger;

import apartment.in.houses.util.DI.context.ApplicationContext;
import apartment.in.houses.util.logger.loghandler.ConsoleLogHandler;
import apartment.in.houses.util.logger.loghandler.FileLogHandler;

import java.util.List;

public class CreateLogger {
    public static CustomLogger create(CustomLogger.LogLevel logLevel, String logName) {
        return new CustomLogger(List.of(
                new FileLogHandler(logName),
                new ConsoleLogHandler()
        ), logLevel);
    }
}
