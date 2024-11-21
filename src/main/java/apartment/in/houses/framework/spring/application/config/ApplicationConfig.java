package apartment.in.houses.framework.spring.application.config;

public class ApplicationConfig {
    private ServerConfig serverConfig;
    private DatabaseConfig databaseConfig;
    private SecurityConfig securityConfig;
    private boolean loaded = false;

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public DatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }

    public void setDatabaseConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public SecurityConfig getSecurityConfig() {
        return securityConfig;
    }

    public void setSecurityConfig(SecurityConfig securityConfig) {
        this.securityConfig = securityConfig;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    @Override
    public String toString() {
        return "ApplicationConfig{" +
                "serverConfig=" + serverConfig +
                ", databaseConfig=" + databaseConfig +
                ", securityConfig=" + securityConfig +
                '}';
    }
}
