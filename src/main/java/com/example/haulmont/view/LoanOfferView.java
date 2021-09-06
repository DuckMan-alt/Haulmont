package com.example.haulmont.view;

import com.example.haulmont.backend.entities.LoanOffer;
import com.example.haulmont.backend.service.LoanOfferService;
import com.example.haulmont.components.LoanOfferEditor;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;

import java.util.List;

@Route("/LoanOffer")
public class LoanOfferView extends VerticalLayout {

    private LoanOfferService loanOfferService;
    private LoanOffer loanOffer = new LoanOffer();
    private LoanOfferEditor loanOfferEditor;

    final TextField filter = new TextField("", "Search by mail or name");
    private final Button addNewBtn = new Button("New client", VaadinIcon.PLUS.create());

    HorizontalLayout header = new HorizontalLayout(filter, addNewBtn);


    Grid<LoanOffer> grid = new Grid<>();

    public LoanOfferView(LoanOfferService loanOfferService, LoanOfferEditor loanOfferEditor) {
        this.loanOfferService = loanOfferService;
        this.loanOfferEditor = loanOfferEditor;

        add(header, loanOfferEditor, grid);

        grid.addColumn(LoanOffer::getNameClient).setHeader("Client");
        grid.addColumn(LoanOffer::getCreditLimit).setHeader("Credit limit");
        grid.addColumn(LoanOffer::getInterestRateClient).setHeader("Interest rate");
        grid.addColumn(LoanOffer::getCredit_sum).setHeader("Credit");
        grid.addColumn(LoanOffer::getTime_interval).setHeader("Time interval");
        grid.addColumn(LoanOffer::getDate).setHeader("Contract date");

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showOffer(e.getValue()));

        grid.asSingleSelect().addValueChangeListener(e -> {
            loanOfferEditor.editLoanOffer(e.getValue());
        });

        addNewBtn.addClickListener(e -> loanOfferEditor.editLoanOffer(new LoanOffer()));

        loanOfferEditor.setChangeHandler(() -> {
            loanOfferEditor.setVisible(false);
            showOffer(filter.getValue());
        });

        showOffer(null);

        add(grid);

    }

    private void showOffer(String filter) {
        if (StringUtils.isEmpty(filter)) {
            List<LoanOffer> loan_offers = loanOfferService.allLoanOffers();

            grid.setItems(loan_offers);
        } else {
            grid.setItems(loanOfferService.findNameOrCreditSumOrCreditLimit(filter));
        }
    }

}
