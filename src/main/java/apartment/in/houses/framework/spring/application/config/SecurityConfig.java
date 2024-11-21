package apartment.in.houses.framework.spring.application.config;

public class SecurityConfig {
    private boolean useHttps;       // Флаг, указывающий, используется ли HTTPS
    private int httpsPort;          // Порт для HTTPS
    private String keystorePath;    // Путь к файлу хранилища ключей (keystore)
    private String keystorePassword;// Пароль к хранилищу ключей

    // Конструктор по умолчанию
    public SecurityConfig() {
    }

    // Геттеры и сеттеры
    public boolean isUseHttps() {
        return useHttps;
    }

    public void setUseHttps(boolean useHttps) {
        this.useHttps = useHttps;
    }

    public int getHttpsPort() {
        return httpsPort;
    }

    public void setHttpsPort(int httpsPort) {
        this.httpsPort = httpsPort;
    }

    public String getKeystorePath() {
        return keystorePath;
    }

    public void setKeystorePath(String keystorePath) {
        this.keystorePath = keystorePath;
    }

    public String getKeystorePassword() {
        return keystorePassword;
    }

    public void setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
    }

    @Override
    public String toString() {
        return "SecurityConfig{" +
                "useHttps=" + useHttps +
                ", httpsPort=" + httpsPort +
                ", keystorePath='" + keystorePath + '\'' +
                ", keystorePassword='" + keystorePassword + '\'' +
                '}';
    }
}
