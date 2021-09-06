package com.example.haulmont.view;

import com.example.haulmont.backend.entities.Client;
import com.example.haulmont.backend.service.ClientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.Route;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Route(value = "/test")
public class MainView extends VerticalLayout{

    private final ClientService clientService;
    private Client client = new Client();

    Binder<Client> binder = new Binder<>(Client.class);



    final TextField name = new TextField("", "name");
    final TextField email = new TextField("", "email");
    final TextField passport_number = new TextField("", "passport_number");
    private HorizontalLayout fieldsCRUD = new HorizontalLayout(name, email, passport_number);


    private String check = "1234567890";



    public MainView(ClientService clientService  ) {
        this.clientService = clientService;
        binder.bindInstanceFields(this);


        binder.forField(name).withValidator(name -> name.length() >= 3, "Full name must contain at least three characters")
                .bind(Client::getName, Client::setName);


        binder.forField(email).withValidator(new EmailValidator("This doesn't look like a valid email address"))
                .bind(Client::getEmail, Client::setEmail);


        binder.forField(passport_number).withValidator(text -> StringUtils.containsOnly(text,check) && text.length() == 10, "Введите корректные данные")
                .bind(Client::getPassport_number, Client::setPassport_number);

        //binder.readBean(client);


        Button resetButton = new Button("Reset",
                event -> binder.readBean(client));

        Button saveButton = new Button("Save");
        saveButton.addClickListener(e -> {
            try {
                save();
            } catch (ValidationException validationException) {
                validationException.printStackTrace();
            }
        });


        add(fieldsCRUD,resetButton, saveButton);



    }



    private void save() throws ValidationException {
        binder.writeBean(client);
        clientService.add(client);
    }

}



