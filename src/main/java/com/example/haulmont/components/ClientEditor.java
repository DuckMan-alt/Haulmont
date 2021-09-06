package com.example.haulmont.components;

import com.example.haulmont.backend.entities.Client;
import com.example.haulmont.backend.service.ClientService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;


@SpringComponent
@UIScope
public class ClientEditor  extends VerticalLayout implements KeyNotifier {

    private final String check = "1234567890";

    private ClientService clientService;
    private Client client;
    public ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }

    Binder<Client> binder = new Binder<>(Client.class);


    private TextField name = new TextField("", "Name");
    private TextField email = new TextField("", "Email");
    private TextField  passport_number = new TextField("", "Passport");
    private TextField phone_number = new TextField("", "Phone");
    private HorizontalLayout fieldsCRUD = new HorizontalLayout(name, email, passport_number, phone_number);



    Button save = new Button("Save");
    Button delete = new Button("Delete");
    private HorizontalLayout btnCRUD = new HorizontalLayout(save,delete);




    @Autowired
    public ClientEditor(ClientService clientService) {
        this.clientService = clientService;

        add(fieldsCRUD,btnCRUD);

        binder.bindInstanceFields(this);

        binder.forField(name).withValidator(name -> name.length() >= 3, "Имя должно содержать минимум 3 символа")
                .bind(Client::getName, Client::setName);


        binder.forField(email).withValidator(new EmailValidator("This doesn't look like a valid email address"))
                .bind(Client::getEmail, Client::setEmail);


        binder.forField(passport_number).withValidator(text -> StringUtils.containsOnly(text,check) && text.length() == 10, "Введите корректные данные")
                .bind(Client::getPassport_number, Client::setPassport_number);

        binder.forField(phone_number).withValidator(text -> (StringUtils.containsOnly(text,check) && text.length() == 11) || (text.isEmpty()), "Введите корректные данные")
                .bind(Client::getPhone_number, Client::setPhone_number);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> {
            try {
                save();
            } catch (ValidationException validationException) {
                validationException.printStackTrace();
            }
        });

        save.addClickListener(e -> {
            try {
                save();
            } catch (ValidationException validationException) {
                validationException.printStackTrace();
            }
        });

        delete.addClickListener(e -> delete());
        setVisible(false);

    }


    void delete() {
        clientService.delete(client);
        changeHandler.onChange();
    }

    private void save() throws ValidationException {
        binder.writeBean(client);
        clientService.add(client);
        changeHandler.onChange();
    }

    public void editClient(Client cln) {
        if (cln == null) {
            setVisible(false);
            return;
        }

        if (cln.getId() != null) {
            this.client = clientService.findById(cln.getId());
        } else {
            this.client = cln;
        }

        binder.setBean(client);

        setVisible(true);

        name.focus();
    }
}

//Кусок для проверки имени
/*
        binder.forField(name)
                // Validator defined based on a lambda and an error message
                .withValidator(
                        name -> name.length() >= 3,
                        "Full name must contain at least three characters")
                .bind(Client::getName, Client::setName);

        //Кусок проверки почты

        binder.forField(email)
                // Explicit validator instance
                .withValidator(new EmailValidator(
                        "This doesn't look like a valid email address"))
                .bind(Client::getEmail, Client::setEmail);

        //Кусок для проверки паспорта и номера телефона

        binder.forField(passport_number)
                .withValidator(text -> StringUtils.containsOnly(text,check) && text.length() == 10,
                        "Введите корректные данные")
                .bind(Client::getPassport_number, Client::setPassport_number);


        binder.forField(phone_number)
                .withValidator(text -> text.length() == 11,
                        "Doesn't look like a passport number")
                .withConverter(
                        new StringToLongConverter("Must enter a number"))

                .bind(Client::getPhone_numberLong, Client::setPhone_numberLong);
*/