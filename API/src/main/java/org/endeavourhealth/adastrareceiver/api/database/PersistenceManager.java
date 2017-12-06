package org.endeavourhealth.adastrareceiver.api.database;

import com.fasterxml.jackson.databind.JsonNode;
import org.endeavourhealth.common.config.ConfigManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class PersistenceManager {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManagerFactory configEntityManagerFactory;

    public static EntityManager getConfigEntityManager() throws Exception {

        if (configEntityManagerFactory == null
                || !configEntityManagerFactory.isOpen()) {
            createConfigEntityManager();
        }

        return entityManagerFactory.createEntityManager();
    }


    public static EntityManager getEntityManager() throws Exception {

        if (entityManagerFactory == null
                || !entityManagerFactory.isOpen()) {
            createEntityManager();
        }

        return entityManagerFactory.createEntityManager();
    }

    public static void ClearEntityManager() throws Exception {
        entityManagerFactory = null;
    }

    private static synchronized void createEntityManager() throws Exception {

        if (entityManagerFactory != null
                && entityManagerFactory.isOpen()) {
            return;
        }

        JsonNode json = ConfigManager.getConfigurationAsJson("adastra_receiverDB");
        String url = json.get("url").asText();
        String user = json.get("username").asText();
        String pass = json.get("password").asText();

        Map<String, Object> properties = new HashMap<>();
        //properties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        properties.put("hibernate.connection.url", url);
        properties.put("hibernate.connection.username", user);
        properties.put("hibernate.connection.password", pass);

        entityManagerFactory = Persistence.createEntityManagerFactory("adastra_receiver", properties);
    }

    private static synchronized void createConfigEntityManager() throws Exception {

        if (configEntityManagerFactory != null
                && configEntityManagerFactory.isOpen()) {
            return;
        }

        JsonNode json = ConfigManager.getConfigurationAsJson("discoveryDashboardDB");
        String url = json.get("url").asText();
        String user = json.get("username").asText();
        String pass = json.get("password").asText();

        Map<String, Object> properties = new HashMap<>();
        //properties.put("hibernate.temp.use_jdbc_metadata_defaults", "false");
        properties.put("hibernate.connection.url", url);
        properties.put("hibernate.connection.username", user);
        properties.put("hibernate.connection.password", pass);

        entityManagerFactory = Persistence.createEntityManagerFactory("discovery_dashboard", properties);
    }
}
