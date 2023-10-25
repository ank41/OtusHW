package ru.otus.services.web.auth;

public interface AuthService {
    boolean authenticate(String login, String password);
}
