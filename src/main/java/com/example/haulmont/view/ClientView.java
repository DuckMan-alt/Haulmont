package com.example.haulmont.view;

import com.example.haulmont.backend.entities.Client;
import com.example.haulmont.backend.service.ClientService;
import com.example.haulmont.components.ClientEditor;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;


@Route(value = "/Client")
public class ClientView extends VerticalLayout {

    private final ClientService clientService;
    private Client client = new Client();
    private ClientEditor clientEditor;

    final TextField filter = new TextField("", "Search by mail or name");
    private final Button addNewBtn = new Button("New client", VaadinIcon.PLUS.create());

    HorizontalLayout header = new HorizontalLayout(filter, addNewBtn);

    Grid<Client> grid = new Grid<>(Client.class);


    public ClientView(ClientService clientSer, ClientEditor clientEditor) {
        this.clientService = clientSer;
        this.clientEditor = clientEditor;

        add(header, clientEditor, grid);

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listClients(e.getValue()));


        grid.asSingleSelect().addValueChangeListener(e -> {
            clientEditor.editClient(e.getValue());
        });

        addNewBtn.addClickListener(e -> clientEditor.editClient(new Client()));

        clientEditor.setChangeHandler(() -> {
            clientEditor.setVisible(false);
            listClients(filter.getValue());
        });

        listClients(null);


    }


    void listClients(String filterText) {
        grid.removeColumnByKey("id");
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(clientService.allClients());
        } else {
            grid.setItems(clientService.findByEmailStartsWithIgnoreCase(filterText));
        }
    }
}
