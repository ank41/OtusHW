package ru.otus.services.web.auth;

import java.io.IOException;
import java.util.Properties;

public class AdminAuthImpl implements AuthService {

    private final String adminLogin;
    private final String adminPassword;


    public AdminAuthImpl() {
        Properties prop = new Properties();
        try(var is =this.getClass().getClassLoader().getResourceAsStream("auth.properties")){
            prop.load(is);
            adminLogin = prop.getProperty("login");
            adminPassword = prop.getProperty("password");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean authenticate(String login, String password) {
        return adminLogin.equals(login) && adminPassword.equals(password);
    }
}
