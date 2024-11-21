package spring.testconfig;

import apartment.in.houses.framework.spring.application.config.ApplicationConfig;
import apartment.in.houses.framework.spring.application.config.LoaderConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoaderConfigTest {

    private ApplicationConfig config;

    @BeforeEach
    public void setUp() {
        LoaderConfig loaderConfig = new LoaderConfig();
        config = loaderConfig.loadConfig();
    }

    @Test
    public void testLoaded() {
        assertNotNull(config);
        assertTrue(config.isLoaded(), "Config should be marked as loaded.");
    }


    @Test
    public void testServerConfig() {
        assertEquals(8080, config.getServerConfig().getPort());
        assertEquals("/", config.getServerConfig().getContextPath());
    }


    @Test
    public void testDatabaseConfig() {
        assertEquals("jdbc:mysql://localhost:3306/ApartmentsInHouses", config.getDatabaseConfig().getUrl());
        assertEquals("root", config.getDatabaseConfig().getUsername());
        assertEquals("nikita090504", config.getDatabaseConfig().getPassword());
        assertEquals("com.mysql.cj.jdbc.Driver", config.getDatabaseConfig().getDriverClassName());
    }



}
