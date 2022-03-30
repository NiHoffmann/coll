package org.hbrs.se2.project.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.hbrs.se2.project.components.Navbar;
import org.hbrs.se2.project.components.Toast;
import org.hbrs.se2.project.components.Profile.CompanyProfile;
import org.hbrs.se2.project.components.Profile.StudentProfile;
import org.hbrs.se2.project.control.SessionControl;
import org.hbrs.se2.project.control.UserControl;

import java.util.HashMap;

/**
 * View responsible for loading ones OWN sidebar etc
 */
@Route(value = "home")
public class Home extends HorizontalLayout implements BeforeEnterObserver {
    final int myID;
    final Navbar navbar;
    /**
     * Components/ views are stored inside this display
     */
    final Div display;

    /**
     * HashMap for the components/views inside the page
     */
    final HashMap<String, Component> views;
    /**
     * Name of the current component/view, use with views.get()
     */
    String currentView;

    public Home() throws Exception {
        getElement().getThemeList().add("dark");
        setHeight("100%");

        views = new HashMap<String, Component>();
        navbar = new Navbar("My profile");
        display = new Div();

        myID = SessionControl.myData.getUserID();
        if (myID == -1) {
            System.out.println("USERID IS STILL -1");
            return;
        }
        // SessionControl.refreshSession(myID);
        
        display.getElement().getStyle().set("padding", "10px").set("margin-top", "44px");
        display.setWidth("100%");
        display.getElement().getThemeList().add("dark");

        navbar.addTab(
            Navbar.createTab(VaadinIcon.HOME, "Start page", StartView.class)
        );

        Button profileButton = new Button("Profile", VaadinIcon.DASHBOARD.create());
        profileButton.setWidth("85%");
        profileButton.addClickListener(event -> {
            if (views.get("Profile") == null) {
                try {
                    System.out.println("USER TYPE " + SessionControl.myData.getUserType());
                    
                    if (SessionControl.myData.getUserType().equals("Student")) {
                        StudentProfile view = new StudentProfile(myID);
                        views.put("Profile", view);
                        display.add(view);
                    }
                    else if (SessionControl.myData.getUserType().equals("Company")) {
                        CompanyProfile view = new CompanyProfile(myID);
                        views.put("Profile", view);
                        display.add(view);
                    }
                } catch (Exception e) {
                    if (e.getMessage() != null) {
                        Toast.open(e.getMessage()).setType("LUMO_ERROR");
                    }
                }
            }
            onButton(profileButton, "Profile");
        });
        navbar.addButton(profileButton);

        Button searchButton = new Button("Search", VaadinIcon.SEARCH.create());
        searchButton.setWidth("85%");
        searchButton.addClickListener(event -> {
            if (views.get("Search") == null) {
                Search view = new Search();
                views.put("Search", view);
                display.add(view);
            }
            onButton(searchButton, "Search");
        });
        navbar.addButton(searchButton);

        Button offerButton = new Button("Search Offers", VaadinIcon.SEARCH.create());
        offerButton.setWidth("85%");
        offerButton.addClickListener(event -> {
            if (views.get("Search") == null) {
                SearchOffer view = new SearchOffer();
                views.put("Search Offers", view);
                display.add(view);
            }
            onButton(offerButton, "Search Offers");
        });
        navbar.addButton(offerButton);


        if (SessionControl.myData.getUserType().equals("Company")) {
            Button createOfferButton = new Button("Create Job", VaadinIcon.PLUS.create());
            createOfferButton.setWidth("85%");
            createOfferButton.addClickListener(event -> {
                if (views.get("CreateJob") == null) {
                    CreateOffer view = new CreateOffer(myID);
                    views.put("CreateJob", view);
                    display.add(view);
                }
                onButton(createOfferButton, "CreateJob");
            });
            
            Button applicationsButton = new Button("Applications", VaadinIcon.ARCHIVE.create());
            applicationsButton.setWidth("85%");
            applicationsButton.addClickListener(event -> {
                if (views.get("Applications") == null) {
                    Applications view = new Applications();
                    views.put("Applications", view);
                    display.add(view);
                }
                onButton(applicationsButton, "Applications");
            });
            navbar.addButton(createOfferButton, applicationsButton);
        }

        Button chatButton = new Button("Chats", VaadinIcon.CHAT.create());
        chatButton.setWidth("85%");
        chatButton.addClickListener(event -> {
            if (views.get("Chat") == null) {
                Chat view = new Chat();
                views.put("Chat", view);
                display.add(view);
            }
            onButton(chatButton, "Chat");
        });
        navbar.addButton(chatButton);

        Button favoriteButton = new Button("Favorites", VaadinIcon.STAR.create());
        favoriteButton.setWidth("85%");
        favoriteButton.addClickListener(event -> {
            if (views.get("Favorites") == null) {
                Favorites view = new Favorites();
                views.put("Favorites", view);
                display.add(view);
            }
            onButton(favoriteButton, "Favorites");
        });
        navbar.addButton(favoriteButton);

        Button settingsButton = new Button("Settings", VaadinIcon.COG.create());
        settingsButton.setWidth("85%");
        settingsButton.addClickListener(event -> {
            if (views.get("Settings") == null) {
                Settings view = new Settings();
                views.put("Settings", view);
                display.add(view);
            }
            onButton(settingsButton, "Settings");
        });
        navbar.addButton(settingsButton);

        Button helpButton = new Button("Help", VaadinIcon.QUESTION_CIRCLE.create());
        helpButton.setWidth("85%");
        helpButton.addClickListener(event -> {
            if (views.get("Help") == null) {
                Help view = new Help();
                views.put("Help", view);
                display.add(view);
            }
            onButton(helpButton, "Help");
        });
        navbar.addButton(helpButton);

        Button feedbackButton = new Button("Feedback", VaadinIcon.INVOICE.create());
        feedbackButton.setWidth("85%");
        feedbackButton.addClickListener(event -> {
            if (views.get("Feedback") == null) {
                Feedback view = new Feedback();
                views.put("Feedback", view);
                display.add(view);
            }
            onButton(feedbackButton, "Feedback");
        });
        navbar.addButton(feedbackButton);

        Button logoutButton = new Button("Logout", VaadinIcon.ARROW_LEFT.create());
        logoutButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        logoutButton.setWidth("85%");
        logoutButton.addClickListener(event -> {
            onButton(logoutButton, null);
            
            UI ui = this.getUI().get();
            ui.getSession().close();

            UserControl.logout();

            ui.getPage().setLocation("/");
        });
        navbar.addButton(logoutButton);

        add(navbar);
        add(display);

        profileButton.click();
    }

    /**
     * Shows component associated with button, hides all other components
     * @param button
     * @param viewName
     */
    private void onButton(Button button, String viewName) {
        //Set all Components that do not equal viewName to invisible
        if (viewName != null) {
            views.entrySet().forEach(entry -> {
                if (!viewName.equals(entry.getKey())) {
                    entry.getValue().setVisible(false);
                }
            });

            views.get(viewName).setVisible(true);
        }

        //Set button to active style
        button.removeThemeVariants(ButtonVariant.LUMO_TERTIARY);
        button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        //Set all other buttons to inactive style
        for (Button button2 : navbar.getButtons()) {
            if (button2 != button) {
                button2.removeThemeVariants(ButtonVariant.LUMO_PRIMARY);
                button2.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
            }
        }
    }

    //Check Cookies Before Enter
    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (SessionControl.myData.getUserID() == -1) {
            System.out.println("REROUTED");
            event.forwardTo("login");
        }
    }
}