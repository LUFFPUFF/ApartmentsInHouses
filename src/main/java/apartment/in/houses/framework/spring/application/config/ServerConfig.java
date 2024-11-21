package apartment.in.houses.framework.spring.application.config;

public class ServerConfig {
    private int port = 8080;
    private String contextPath = "/";
    private String host;
    private String webAppDir;

    public ServerConfig() {
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getContextPath() {
        return contextPath;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getWebAppDir() {
        return webAppDir;
    }

    public void setWebAppDir(String webAppDir) {
        this.webAppDir = webAppDir;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "port=" + port +
                ", contextPath='" + contextPath + '\'' +
                '}';
    }
}
