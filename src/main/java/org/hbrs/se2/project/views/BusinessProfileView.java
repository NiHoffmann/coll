package org.hbrs.se2.project.views;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.*;

import org.hbrs.se2.project.components.Navbar;
import org.hbrs.se2.project.util.Globals;

import java.sql.SQLException;

@Route(value = Globals.Pages.BUSSINESS_PROFILE_VIEW)
@RouteAlias(value = "business-profile")
public class BusinessProfileView extends HorizontalLayout {

    public BusinessProfileView() throws SQLException {
        setup();
    }

    public void setup() throws SQLException {
        Navbar navbar = new Navbar("Bussiness");
        add(navbar);
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        add(new H3("Unternehmen")); //get unternehmen data from db
        //TODO WRITE PROPPER METHODE
        //JDBCConnection.exec();
    }

}