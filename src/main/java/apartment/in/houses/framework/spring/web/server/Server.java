package apartment.in.houses.framework.spring.web.server;

import apartment.in.houses.framework.spring.application.config.ApplicationConfig;
import apartment.in.houses.framework.spring.application.config.LoaderConfig;
import apartment.in.houses.framework.spring.application.config.ServerConfig;
import apartment.in.houses.framework.spring.web.server.exception.ServerException;
import org.apache.catalina.LifecycleException;

public class Server {
    private LoaderConfig loaderConfig = new LoaderConfig();
    private ApplicationConfig applicationConfig = loaderConfig.loadConfig();
    private TomcatServer tomcatServer;

    public Server() {
        this.tomcatServer = new TomcatServer(new LifecycleManager());
    }

    public void startServer() {
        try {
            if (applicationConfig.isLoaded()) {
                ServerConfig serverConfig = applicationConfig.getServerConfig();

                tomcatServer.setPort(serverConfig.getPort());
                tomcatServer.setHost(serverConfig.getHost());
                tomcatServer.setWebAppDir("src/main/webapp");


                //TODO доработать настройку безопасности
                if (applicationConfig.getSecurityConfig() != null) {
                    tomcatServer.setupSecurity(applicationConfig.getSecurityConfig());
                }

                tomcatServer.configure("src/main/webapp");
                tomcatServer.start();
            } else {
                throw new IllegalStateException("ApplicationConfig not loaded.");
            }
        } catch (ServerException | LifecycleException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopServer() {
        try {
            tomcatServer.stop();
        } catch (ServerException | LifecycleException e) {
            throw new RuntimeException(e);
        }
    }
}
