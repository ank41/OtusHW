package ru.otus;

import org.hibernate.cfg.Configuration;
import ru.otus.domain.Address;
import ru.otus.domain.Client;
import ru.otus.domain.Phone;
import ru.otus.persistance.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.persistance.repository.DataTemplateHibernate;
import ru.otus.persistance.repository.HibernateUtils;
import ru.otus.persistance.sessionmanager.TransactionManagerHibernate;
import ru.otus.services.db.DBServiceClient;
import ru.otus.services.db.DbServiceClientImpl;
import ru.otus.services.web.auth.AdminAuthImpl;
import ru.otus.services.web.auth.AuthService;
import ru.otus.services.web.server.ClientWebServer;
import ru.otus.services.web.server.ClientWebServerWithFilterBasedSecurity;
import ru.otus.services.web.template.TemplateProcessor;
import ru.otus.services.web.template.TemplateProcessorImpl;

/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerWithBasicSecurity {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";


    public static void main(String[] args) throws Exception {
        DBServiceClient dbServiceClient = setUpClientService();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        AuthService authService = new AdminAuthImpl();

        ClientWebServer webServer = new ClientWebServerWithFilterBasedSecurity(WEB_SERVER_PORT,
                authService, dbServiceClient, templateProcessor);

        webServer.start();
        webServer.join();
    }

    private static DBServiceClient setUpClientService(){
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        return new DbServiceClientImpl(transactionManager, clientTemplate);
    }
}
