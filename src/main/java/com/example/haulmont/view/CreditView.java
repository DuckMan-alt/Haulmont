package com.example.haulmont.view;

import com.example.haulmont.backend.entities.Credit;
import com.example.haulmont.backend.service.CreditService;
import com.example.haulmont.components.CreditEditor;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value = "/Credit")
public class CreditView extends VerticalLayout {

    private final CreditService creditService;
    private Credit credit = new Credit();
    private CreditEditor creditEditor;

    private final Button addNewBtn = new Button("New client", VaadinIcon.PLUS.create());

    HorizontalLayout header = new HorizontalLayout(addNewBtn);

    Grid<Credit> grid = new Grid<>(Credit.class);

    public CreditView(CreditService creditService,CreditEditor creditEditor) {
        this.creditService = creditService;
        this.creditEditor = creditEditor;

        add(header,creditEditor,grid);

        grid.asSingleSelect().addValueChangeListener(e -> {
            creditEditor.editCredit(e.getValue());
        });

        addNewBtn.addClickListener(e -> creditEditor.editCredit(new Credit()));

        creditEditor.setChangeHandler(() -> {
            creditEditor.setVisible(false);
            ShowCredits();
        });

        ShowCredits();
    }

    private void ShowCredits() {
        grid.setItems(creditService.allCredits());
    }
}
