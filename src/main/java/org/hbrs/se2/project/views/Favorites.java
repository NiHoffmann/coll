package org.hbrs.se2.project.views;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;

import org.hbrs.se2.project.classes.User.Company;

public class Favorites extends Div {

    public Favorites() {
        getElement().getThemeList().add("dark");

        H2 header = new H2("Favorites");
        Grid<Company> grid = new Grid<>(Company.class, false);

        grid.addColumn(Company::getName).setHeader("Company name");
        grid.addColumn(Company::getStreet).setHeader("Street");
        grid.addColumn(Company::getZip).setHeader("Zip");
        grid.addColumn(Company::getCity).setHeader("City");
        grid.addColumn(Company::getCountry).setHeader("Country");

        add(header, grid);
    }
}
