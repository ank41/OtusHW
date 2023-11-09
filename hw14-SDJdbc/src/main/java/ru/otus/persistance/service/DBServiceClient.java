package ru.otus.persistance.service;

import ru.otus.domain.Client;
import ru.otus.dto.ClientDto;

import java.util.List;
import java.util.Optional;

public interface DBServiceClient {

    List<Client> findAll();

    Client save(Client client);
}
