package apartment.in.houses.util.DI.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.io.File;
import java.net.URL;
import java.util.stream.Stream;

public class PackageScannerUtil {

    public static List<Class<?>> scanClasses(String packageName) throws ClassNotFoundException, IOException {
        List<Class<?>> classes = new ArrayList<>();
        String packagePath = packageName.replace(".", "/");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = classLoader.getResources(packagePath);
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            File directory = new File(resource.getFile());
            findClasses(directory, packageName, classes);
        }
        return classes;
    }

    private static void findClasses(File directory, String packageName, List<Class<?>> classes) throws ClassNotFoundException {
        try (Stream<Path> paths = Files.walk(directory.toPath())) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".class"))
                    .forEach(path -> {
                        String className = packageName + "." + path.toFile().getName().substring(0, path.toFile().getName().length() - 6);
                        try {
                            Class<?> clazz = Class.forName(className);
                            classes.add(clazz);
                        } catch (ClassNotFoundException e) {
                            System.err.println("Ошибка: " + e.getMessage());
                        }
                    });
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
    }
}
