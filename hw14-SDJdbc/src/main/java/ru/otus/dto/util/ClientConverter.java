package ru.otus.dto.util;

import org.springframework.stereotype.Service;
import ru.otus.domain.Address;
import ru.otus.domain.Client;
import ru.otus.domain.Phone;
import ru.otus.dto.ClientDto;

import java.util.Arrays;
import java.util.Set;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.*;

@Service
public class ClientConverter {

    public Client toClient(ClientDto clientDTO) {
        var phonesStr = clientDTO.getPhones();
        Set<Phone> phones = null;
        if (nonNull(phonesStr) && !phonesStr.isBlank()) {
            phones = Arrays.stream(phonesStr.split(","))
                    .map(Phone::new)
                    .collect(toSet());
        }
        return new Client(
                clientDTO.getId(),
                clientDTO.getName(),
                new Address(null, clientDTO.getAddress()),
                phones
        );
    }


    public ClientDto toDTO(Client client) {
        ClientDto clientDto = new ClientDto();

        clientDto.setId(client.getId());
        clientDto.setName(client.getName());

        if (nonNull(client.getAddress())) {
            clientDto.setAddress(
                    client.getAddress().getStreet()
            );
        }

        if (nonNull(client.getPhoneList())) {
            clientDto.setPhones(
                    client.getPhoneList().stream().map(Phone::getNumber).collect(joining(","))
            );
        }
        return clientDto;
    }
}
