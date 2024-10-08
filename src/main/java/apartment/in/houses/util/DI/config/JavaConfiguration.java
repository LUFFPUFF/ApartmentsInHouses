package apartment.in.houses.util.DI.config;

import java.io.IOException;
import java.util.List;

public class JavaConfiguration implements Configuration {

    private final String basePackage;

    public JavaConfiguration(String basePackage) {
        this.basePackage = basePackage;
    }

    @Override
    public List<Class<?>> scan() throws IOException, ClassNotFoundException {
        return PackageScannerUtil.scanClasses(basePackage);
    }
}
