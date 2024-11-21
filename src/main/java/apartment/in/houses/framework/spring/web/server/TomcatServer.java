package apartment.in.houses.framework.spring.web.server;

import apartment.in.houses.framework.spring.application.config.SecurityConfig;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;

public class TomcatServer {

    private final Tomcat tomcat;

    private final LifecycleManager lifecycleManager;

    public TomcatServer(LifecycleManager lifecycleManager) {
        this.lifecycleManager = lifecycleManager;
        this.tomcat = new Tomcat();
    }

    public void setPort(int port) {
        tomcat.setPort(port);
    }

    public void setHost(String host) {
        tomcat.getHost().setName(host);
    }

    public void setWebAppDir(String webAppDir) {
        tomcat.setBaseDir(webAppDir);
    }

    public void setupSecurity(SecurityConfig securityConfig) {
        //TODO доработать
        if (securityConfig.isUseHttps()) {
            Connector sslConnector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
            sslConnector.setPort(securityConfig.getHttpsPort());
            tomcat.getService().addConnector(sslConnector);
        }
    }

    public void configure(String webAppDir) {
        tomcat.addWebapp("/", webAppDir);
    }

    public void start() throws org.apache.catalina.LifecycleException {
        lifecycleManager.startServer(tomcat);
    }

    public void stop() throws org.apache.catalina.LifecycleException {
        lifecycleManager.stopServer(tomcat);
    }
}
