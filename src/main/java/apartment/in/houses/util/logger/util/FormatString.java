package apartment.in.houses.util.logger.util;

import apartment.in.houses.util.logger.CustomLogger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatString {
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String formatMessage(CustomLogger.LogLevel level, String message) {
        LocalDateTime currentTime = LocalDateTime.now();
        return "[" + formatter.format(currentTime) + "] [" + level + "] " + message;
    }
}
