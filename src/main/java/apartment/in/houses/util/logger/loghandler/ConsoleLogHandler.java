package apartment.in.houses.util.logger.loghandler;

import apartment.in.houses.util.logger.loghandler.LogHandler;

public class ConsoleLogHandler implements LogHandler {
    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
