package org.hbrs.se2.project.views;

import java.sql.SQLException;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import org.hbrs.se2.project.classes.User.Offer;
import org.hbrs.se2.project.components.FloatingDiv;
import org.hbrs.se2.project.components.Toast;
import org.hbrs.se2.project.control.ApplicationControl;
import org.hbrs.se2.project.control.SearchControl;
import org.hbrs.se2.project.control.SessionControl;

public class SearchOffer extends VerticalLayout {
    final Grid<Offer> grid;
    final FloatingDiv currentOfferWindow;

    public SearchOffer() {
        currentOfferWindow = new FloatingDiv();
        currentOfferWindow.setVisible(false);
        currentOfferWindow.setWidth("75%");
        currentOfferWindow.setHeight("35%");

        getElement().getThemeList().add("dark");

        H2 header = new H2("Search");
        TextField searchField = new TextField("Search for a Job Offer");
        searchField.setPlaceholder("Search...");

        grid = new Grid<>(Offer.class, false);


        grid.addColumn(Offer::getName).setHeader("Company name");
        grid.addColumn(Offer::getPosition).setHeader("Position");
        grid.addColumn(Offer::getDescription).setHeader("Description");

        try {
            grid.setItems(SearchControl.searchOffer(null));
        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
 
        searchField.addBlurListener(event -> {
            String search = searchField.getValue();

            try {
                grid.setItems(SearchControl.searchOffer(search));
            } catch (SQLException e) {
                Toast.open(e.getMessage()).setType("LUMO_ERROR");
            }
            //TODO GET companies where isHiring = true
        });


        grid.addItemClickListener(itemEvent -> {
            Offer offer = itemEvent.getItem();
            
            closeOfferWindow();
            currentOfferWindow.setVisible(true);

            Button closeButton = new Button(VaadinIcon.CLOSE_SMALL.create(), clickEvent -> {
                closeOfferWindow();
            });

            VerticalLayout offerView = new VerticalLayout();

            offerView.setHorizontalComponentAlignment(Alignment.END, closeButton);
            offerView.add(closeButton);

            Text title = new Text("Schreibe hier deine Bewerbung: ");
            TextArea beweburg = new TextArea();
            beweburg.setWidth("100%");

            offerView.add(title);
            offerView.add(beweburg);

            Button sendButton = new Button("send", clickEvent -> {
                sendApplication(offer, beweburg.getValue());
                closeOfferWindow();
            });
            offerView.setHorizontalComponentAlignment(Alignment.END, sendButton);
            offerView.add(sendButton);

            currentOfferWindow.add(offerView);
        });

        add(header, searchField, grid, currentOfferWindow);
    }

    public void closeOfferWindow() {
        currentOfferWindow.removeAll();
        currentOfferWindow.setVisible(false);
    }

    public void sendApplication(Offer offer, String text){
        int user_id = SessionControl.myData.getUserID();
        int offer_id = offer.getId();
        try {
            ApplicationControl.apply(offer_id, user_id, text);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
