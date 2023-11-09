package ru.otus.persistance.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.repository.ListCrudRepository;
import ru.otus.domain.Client;

public interface ClientRepository extends ListCrudRepository<Client, Long> {
}
