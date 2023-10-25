package ru.otus.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.SEQUENCE;
import static java.util.Objects.nonNull;


@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @SequenceGenerator(name = "client_gen", sequenceName = "client_seq",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "client_gen")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = ALL, fetch = EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(cascade = ALL, fetch = EAGER)
    @JoinColumn(name = "client_id", nullable = false, updatable = false)
    private List<Phone> phoneList;

    public Client() {
    }

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
    }


    public Client(Long id, String name, Address address, List<Phone> phoneList) {
        this.id = id;
        this.name = name;
        if (nonNull(address)) {
            this.address = address.clone();
            this.address.setClient(this);
        }
        if (nonNull(address)) {
            this.phoneList = clonePhoneList(phoneList);
        }
    }

    @Override
    public Client clone() {
        Client client = new Client(this.id, this.name);
        if (nonNull(this.address)) {
            Address address = this.address.clone();
            address.setClient(client);
            client.setAddress(address);
        }
        if (nonNull(phoneList)) {
            List<Phone> phones = clonePhoneList(this.phoneList);
            client.setPhoneList(phones);
        }
        return client;
    }

    private List<Phone> clonePhoneList(List<Phone> phoneList) {
        return phoneList.stream()
                .map(Phone::clone)
                .toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phoneList=" + phoneList +
                '}';
    }
}

