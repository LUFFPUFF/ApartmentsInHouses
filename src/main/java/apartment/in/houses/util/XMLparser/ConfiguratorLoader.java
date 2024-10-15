package apartment.in.houses.util.XMLparser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ConfiguratorLoader {
    private static final Map<Class<?>, String> entityMappings = new HashMap<>();
    private static final String configPath = "src/main/resources/jdbc-config.xml";
    static InputStream inputStream = ConfiguratorLoader.class.getClassLoader().getResourceAsStream("jdbc-config.xml");

    static {
        try {
            if (inputStream == null) {
                throw new RuntimeException("Configuration file not found: " + configPath);
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputStream);
            doc.getDocumentElement().normalize();

            NodeList entities = doc.getElementsByTagName("entity");
            for (int i = 0; i < entities.getLength(); i++) {
                Element entityElement = (Element) entities.item(i);
                String className = entityElement.getAttribute("class");
                String tableName = entityElement.getAttribute("table");

                Class<?> entityClass = Class.forName(className);
                entityMappings.put(entityClass, tableName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading configuration file: " + e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String getTableName(Class<?> entityClass) {
        return entityMappings.get(entityClass);
    }

    public static boolean isEntityMapped(Class<?> entityClass) {
        return entityMappings.containsKey(entityClass);
    }
}
