package com.example.haulmont.components;

import com.example.haulmont.backend.entities.Client;
import com.example.haulmont.backend.entities.Credit;
import com.example.haulmont.backend.service.CreditService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToLongConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class CreditEditor extends VerticalLayout implements KeyNotifier {

    private final String check = "1234567890";


    private CreditService creditService;
    private Credit credit;

    public ClientEditor.ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    public void setChangeHandler(ClientEditor.ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }



    Binder<Credit> binder = new Binder<>(Credit.class);

    private TextField credit_limit = new TextField("","credit_limit");
    private NumberField interest_rate = new NumberField("", "interest_rate");
    private VerticalLayout fieldsCRUD = new VerticalLayout(credit_limit ,interest_rate);


    Button save = new Button("Save");
    Button delete = new Button("Delete");
    private HorizontalLayout btnCRUD = new HorizontalLayout(save,delete);



    @Autowired
    public CreditEditor(CreditService creditService) {
        this.creditService = creditService;

        add(fieldsCRUD,btnCRUD);

        binder.bindInstanceFields(this);

        binder.forField(credit_limit).withValidator(text -> StringUtils.containsOnly(text,check), "Введите корректные данные")
                .bind(Credit::getCredit_limit, Credit::setCredit_limit);

        binder.forField(interest_rate)
                .withValidator(text -> text>0,"Error")
                .bind(Credit::getInterest_rate,Credit::setInterest_rate);



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

    private void delete() {
        creditService.delete(credit);
        changeHandler.onChange();
    }

    private void save() throws ValidationException{
        binder.writeBean(credit);
        creditService.add(credit);
        changeHandler.onChange();

    }

    public void editCredit(Credit crdt) {
        if (crdt == null) {
            setVisible(false);
            return;
        }

        if (crdt.getId() != null) {
            this.credit = creditService.findById(crdt.getId());
        } else {
            this.credit = crdt;
        }

        binder.readBean(credit);

        setVisible(true);

        credit_limit.focus();
    }

}
