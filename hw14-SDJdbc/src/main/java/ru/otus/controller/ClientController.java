package ru.otus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.dto.ClientDto;
import ru.otus.dto.util.ClientConverter;
import ru.otus.persistance.service.DBServiceClient;

import static java.util.stream.Collectors.toList;

@Controller
@RequestMapping("/api")
public class ClientController {
    private final DBServiceClient dbServiceClient;
    private final ClientConverter converterDTO;

    @Autowired
    public ClientController(DBServiceClient dbServiceClient, ClientConverter converterDTO) {
        this.dbServiceClient = dbServiceClient;
        this.converterDTO = converterDTO;
    }

    @GetMapping("/clients")
    public String getClients(Model model) {
        model.addAttribute(
                "clients",
                dbServiceClient.findAll().stream().map(converterDTO::toDTO).collect(toList())
        );
        return "clients";
    }

    @PostMapping("/client")
    public RedirectView addClient(@ModelAttribute ClientDto clientTransferObject) {
        var client = converterDTO.toClient(clientTransferObject);
        dbServiceClient.save(client);
        return new RedirectView("/api/clients", true);
    }

}
