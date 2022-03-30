package org.hbrs.se2.project.views;

import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import org.hbrs.se2.project.components.Navbar;
import org.hbrs.se2.project.util.Globals;

@Route(value = Globals.Pages.ABOUT_VIEW)
@RouteAlias(value = "about")
public class About extends HorizontalLayout {
    public About(){
        setup();
    }

    public void setup() {
        Navbar navbar = new Navbar("About");
        add(navbar);
        add(new Html(html()));
    }

    public String html(){
        String html_code = "<h1>About Coll...</h1>";
        return html_code;
    }
}
