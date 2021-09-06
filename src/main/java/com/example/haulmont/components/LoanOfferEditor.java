package com.example.haulmont.components;

import com.example.haulmont.backend.entities.Client;
import com.example.haulmont.backend.entities.Credit;
import com.example.haulmont.backend.entities.LoanOffer;
import com.example.haulmont.backend.service.ClientService;
import com.example.haulmont.backend.service.CreditService;
import com.example.haulmont.backend.service.LoanOfferService;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@SpringComponent
@UIScope
public class LoanOfferEditor extends VerticalLayout implements KeyNotifier {

    private final String check = "1234567890";


    private LoanOfferService loanOfferService;
    private ClientService clientService;
    private CreditService creditService;
    private LoanOffer loanOffer = new LoanOffer();
    public ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChange();
    }

    public void setChangeHandler(ChangeHandler changeHandler) {
        this.changeHandler = changeHandler;
    }

    Binder<LoanOffer> binder = new Binder<>(LoanOffer.class);


    private Select<Client> clients = new Select<>();
    private Select<Credit> credits = new Select<>();
    Button resume = new Button("Continue");


    private TextField creditSum = new TextField("Сумма кредита", "Сумма в рублях");
    private IntegerField timeInterval = new IntegerField("Срок кредита", "Кол-во месяцев");
    private HorizontalLayout fieldsCRUD2 = new HorizontalLayout(clients,credits,creditSum, timeInterval);


    Button save = new Button("Save");
    Button delete = new Button("Delete");
    private HorizontalLayout btnCRUD = new HorizontalLayout(save,delete);

    @Autowired
    public LoanOfferEditor(LoanOfferService loanOfferService, ClientService clientService, CreditService creditService) {
        this.loanOfferService = loanOfferService;
        this.clientService = clientService;
        this.creditService = creditService;

        add(clients,credits,resume,fieldsCRUD2,btnCRUD);
        credits.setReadOnly(false);
        clients.setReadOnly(false);
        clients.setItemLabelGenerator(Client::getName);
        clients.setItems(clientService.allClients());

        credits.setItemLabelGenerator(Credit::getCredit_limit);
        credits.setItems(creditService.allCredits());
        credits.setValue(creditService.allCredits().get(0));

        resume.addClickListener(e -> {
            try {
                fields();
            } catch (ValidationException validationException) {
                validationException.printStackTrace();
            }
        });


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

    void fields() throws ValidationException {
        loanOffer.setClient(clients.getValue());
        loanOffer.setCredit(credits.getValue());
        credits.setReadOnly(true);
        clients.setReadOnly(true);
        fieldsCRUD2.setVisible(true);
        btnCRUD.setVisible(true);
        int crdtlmt = Integer.parseInt(loanOffer.getCredit().getCredit_limit());
        binder.forField(creditSum).withValidator(text -> StringUtils.containsOnly(text,check) && text.charAt(0) != 0, "Введите корректные данные")
                .withValidator(value -> (Integer.parseInt(value) <= crdtlmt ),"Введите сумму меньше кредитного лимита")
                .bind(LoanOffer::getCredit_sum, LoanOffer::setCredit_sum);

        binder.forField(timeInterval).withValidator(text -> text>0, "Введите корректные данные")
                .bind(LoanOffer::getTime_interval, LoanOffer::setTime_interval);

        loanOffer.setDate(LocalDate.now());
    }

    private void delete() {
        loanOfferService.delete(loanOffer);
        changeHandler.onChange();
    }

    private void save() throws ValidationException{
        binder.writeBean(loanOffer);
        loanOfferService.add(loanOffer);
        changeHandler.onChange();
    }

    public void editLoanOffer(LoanOffer lonOf) {
        if (lonOf == null) {
            setVisible(false);
            return;
        }

        if (lonOf.getId() != null) {
            this.loanOffer = loanOfferService.findById(lonOf.getId());
        } else {
            this.loanOffer = lonOf;
        }

        binder.setBean(loanOffer);



        fieldsCRUD2.setVisible(false);
        btnCRUD.setVisible(false);

        setVisible(true);
    }
}
