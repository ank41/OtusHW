package ru.otus.services.web.server;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.services.db.DBServiceClient;
import ru.otus.services.web.template.TemplateProcessor;
import ru.otus.services.web.auth.AuthService;
import ru.otus.services.web.servlet.AuthorizationFilter;
import ru.otus.services.web.servlet.LoginServlet;

import java.util.Arrays;

public class ClientWebServerWithFilterBasedSecurity extends ClientWebServerSimple {
    private final AuthService authService;

    public ClientWebServerWithFilterBasedSecurity(int port,
                                                  AuthService authService,
                                                  DBServiceClient dbServiceClient,
                                                  TemplateProcessor templateProcessor) {
        super(port, dbServiceClient, templateProcessor);
        this.authService = authService;
    }

    @Override
    protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, authService)), "/login");
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths)
                .forEachOrdered(
                        path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }
}
