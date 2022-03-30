package org.hbrs.se2.project.views;

import java.sql.SQLException;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import org.hbrs.se2.project.classes.User.Company;
import org.hbrs.se2.project.components.FloatingDiv;
import org.hbrs.se2.project.components.Toast;
import org.hbrs.se2.project.components.Profile.CompanyProfile;
import org.hbrs.se2.project.control.SearchControl;
import org.hbrs.se2.project.control.SessionControl;

public class Search extends VerticalLayout {
    final Grid<Company> grid;
    final FloatingDiv currentCompanyWindow;

    public Search() {
        getElement().getThemeList().add("dark");

        H2 header = new H2("Search");
        TextField searchField = new TextField("Search for a company");
        searchField.setPlaceholder("Search...");

        grid = new Grid<>(Company.class, false);
        currentCompanyWindow = new FloatingDiv();

        currentCompanyWindow.setVisible(false);
        currentCompanyWindow.setWidth("75%");
        currentCompanyWindow.setHeight("75%");

        grid.addColumn(Company::getName).setHeader("Company name");
        grid.addColumn(Company::getStreet).setHeader("Street");
        grid.addColumn(Company::getZip).setHeader("Zip");
        grid.addColumn(Company::getCity).setHeader("City");
        grid.addColumn(Company::getCountry).setHeader("Country");
        grid.addColumn(Company::getHiring).setHeader("Is hiring");

        try {
            grid.setItems(SearchControl.search(null));
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        searchField.addBlurListener(event -> {
            String search = searchField.getValue();

            try {
                grid.setItems(SearchControl.search(search));
            } catch (SQLException e) {
                Toast.open(e.getMessage()).setType("LUMO_ERROR");
            }
            //TODO GET companies where isHiring = true
        });

        grid.addItemClickListener(itemEvent -> {
            Company company = itemEvent.getItem();
            
            closeCompanyWindow();
            currentCompanyWindow.setVisible(true);

            VerticalLayout companyView = new VerticalLayout();
            Button closeButton = new Button(VaadinIcon.CLOSE_SMALL.create(), clickEvent -> {
                closeCompanyWindow();
            });
            
            companyView.setHorizontalComponentAlignment(Alignment.END, closeButton);

            CompanyProfile profile = new CompanyProfile(company);

            companyView.addDetachListener(detachEvent -> {
                closeCompanyWindow();
            });

            companyView.add(closeButton, profile);

            System.out.println(SessionControl.myData.getUserType());

            if (SessionControl.myData.getUserType().equals("Student")) {
                Button applyButton = new Button("Apply", clickEvent -> {
                    //TODO apply
                });
                applyButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

                companyView.add(applyButton);
            }

            currentCompanyWindow.add(companyView);
        });

        add(header, searchField, grid, currentCompanyWindow);
    }

    public void closeCompanyWindow() {
        currentCompanyWindow.removeAll();
        currentCompanyWindow.setVisible(false);
    }
}
