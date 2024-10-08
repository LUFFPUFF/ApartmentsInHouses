package apartment.in.houses.util.logger;

import apartment.in.houses.util.DI.annotation.Component;
import apartment.in.houses.util.logger.loghandler.LogHandler;
import apartment.in.houses.util.logger.util.FormatString;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class CustomLogger {

    public enum LogLevel {
        TRACE, DEBUG, INFO, WARNING, ERROR, FATAL
    }

    private final List<LogHandler> logHandlers;
    private final LogLevel currentLogLevel;

    public CustomLogger(List<LogHandler> logHandlers, LogLevel currentLogLevel) {
        this.logHandlers = logHandlers;
        this.currentLogLevel = currentLogLevel;
    }

    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    public void warning(String message) {
        log(LogLevel.WARNING, message);
    }

    public void log(LogLevel logLevel, String message) {
        if (logLevel.ordinal() >= currentLogLevel.ordinal()) {
            String formattedMessage = FormatString.formatMessage(logLevel, message);
            for (LogHandler handler : logHandlers) {
                handler.log(formattedMessage);
            }
        }
    }

}
