package apartment.in.houses.util.DI.config;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface Configuration {
    List<Class<?>> scan() throws IOException, ClassNotFoundException;
}
