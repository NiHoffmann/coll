package org.hbrs.se2.project.views;

import java.sql.SQLException;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import org.hbrs.se2.project.classes.User.Application;
import org.hbrs.se2.project.components.FloatingDiv;
import org.hbrs.se2.project.control.ApplicationControl;
import org.hbrs.se2.project.control.SearchControl;
import org.hbrs.se2.project.control.SessionControl;

public class Applications extends VerticalLayout {
    final Grid<Application> grid;
    final FloatingDiv currentApplicationWindow;

    public Applications() {
        getElement().getThemeList().add("dark");

        H2 header = new H2("Applications");
        TextField searchField = new TextField("Search for applicants");
        searchField.setPlaceholder("Search...");

        grid = new Grid<>(Application.class, false);
        currentApplicationWindow = new FloatingDiv();

        currentApplicationWindow.setVisible(false);
        currentApplicationWindow.setWidth("75%");
        currentApplicationWindow.setHeight("30%");

        grid.addColumn(Application::getForName).setHeader("First name");
        grid.addColumn(Application::getLastName).setHeader("Last name");
        grid.addColumn(Application::getPosition).setHeader("Position");
        grid.addColumn(Application::getStatus).setHeader("Status");

        updateGrid(null);

        searchField.addBlurListener(event -> {
            String search = searchField.getValue();
            updateGrid(search);
        });

        grid.addItemClickListener(itemEvent -> {
            closeApplicationWindow();

            currentApplicationWindow.setVisible(true);

            VerticalLayout applicationView = new VerticalLayout();

            Button closeButton = new Button(VaadinIcon.CLOSE_SMALL.create(), clickEvent -> {
                closeApplicationWindow();
            });
            applicationView.setHorizontalComponentAlignment(Alignment.END, closeButton);
            applicationView.add(closeButton);

            applicationView.add(closeButton);

            applicationView.addDetachListener(detachEvent -> {
                closeApplicationWindow();
            });


            TextField applicationText = new TextField();
            applicationText.setWidth("100%");
            applicationText.setEnabled(false);

            if(itemEvent.getItem() != null && itemEvent.getItem().getApplicationText() != null){
                applicationText.setValue(itemEvent.getItem().getApplicationText());
            }

            applicationView.add(applicationText);

            Button acceptButton = new Button("Accept", event -> {
                ApplicationControl.acceptStudent(itemEvent.getItem().getId());
                String search = searchField.getValue();
                updateGrid(search);
                closeApplicationWindow();
            });
            acceptButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);

            Button rejectButton = new Button("Deny", event -> {
                ApplicationControl.rejectStudent(itemEvent.getItem().getId());
                String search = searchField.getValue();
                updateGrid(search);
                closeApplicationWindow();
            });
            rejectButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_PRIMARY);

            Div buttonHolder = new Div();
            buttonHolder.setWidth("100%");
            buttonHolder.add(acceptButton, rejectButton);

            applicationView.add(buttonHolder);

            currentApplicationWindow.add(applicationView);
        });

        add(header, searchField, grid, currentApplicationWindow);
    }

    public void closeApplicationWindow() {
        currentApplicationWindow.removeAll();
        currentApplicationWindow.setVisible(false);
    }

    public void updateGrid(String search){
        try {
            grid.setItems(SearchControl.searchApplicants(search, SessionControl.getUserIDFromSession()));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
