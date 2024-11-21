package apartment.in.houses.framework.spring.application.config;

import apartment.in.houses.framework.spring.application.config.util.LoadUtil;
import apartment.in.houses.util.logger.CreateLogger;
import apartment.in.houses.util.logger.CustomLogger;

public class LoaderConfig {
    private static final CustomLogger logger = CreateLogger.create(CustomLogger.LogLevel.INFO, "log/loader.log");

    public ApplicationConfig loadConfig() {
        ApplicationConfig config = new ApplicationConfig();

        config = LoadUtil.loadFromJson(config);
        if (config.isLoaded()) {
            return config;
        }

        config = LoadUtil.loadFromYaml(config);
        if (config.isLoaded()) {
            return config;
        }

        // Попытка загрузки из Properties
        config = LoadUtil.loadFromProperties(config);
        if (config.isLoaded()) {
            return config;
        }

        logger.warning("Configuration file not found. Using default settings.");
        return config;
    }
}
