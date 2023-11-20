package ru.otus.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;


@Table(name = "client")
public class Client {

    @Id
    private final Long id;


    private final String name;

    @MappedCollection(idColumn = "client_id")
    private final Address address;

    @MappedCollection(idColumn = "client_id")
    private final Set<Phone> phoneList;

    @PersistenceCreator
    public Client(Long id, String name, Address address, Set<Phone> phoneList) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneList = phoneList;
    }


    public Long getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public Address getAddress() {
        return address;
    }


    public Set<Phone> getPhoneList() {
        return phoneList;
    }
}

