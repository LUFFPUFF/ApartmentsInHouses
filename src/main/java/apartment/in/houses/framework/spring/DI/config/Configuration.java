package apartment.in.houses.framework.spring.DI.config;

import java.io.IOException;
import java.util.List;

public interface Configuration {
    List<Class<?>> scan() throws IOException, ClassNotFoundException;
}
