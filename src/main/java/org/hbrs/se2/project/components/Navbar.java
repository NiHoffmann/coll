package org.hbrs.se2.project.components;

import java.util.ArrayList;
// import java.util.Optional;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.*;

/**
 * Default side navigation component
 * @see ProfileNavbar
 */
public class Navbar extends AppLayout {
    protected final Tabs tabsComponent;
    protected final ArrayList<Button> buttons = new ArrayList<Button>();
    //protected final HashMap<Tab, Class<? extends Component>> tabMap = new HashMap<Tab, Class<? extends Component>>();

    public Navbar(String title) {
        this.getElement().getThemeList().add("dark");

        DrawerToggle toggle = new DrawerToggle();
        toggle.setHeight("100%");
        
        tabsComponent = new Tabs();
        tabsComponent.setHeight("100%");
        tabsComponent.setOrientation(Tabs.Orientation.VERTICAL);
        tabsComponent.getElement().getThemeList().add("dark");
        tabsComponent.setId("tabs");
        tabsComponent.getElement().getStyle().set("margin-top", "0px");

        H1 header = new H1(title);
        header.getStyle()
            .set("font-size", "var(--lumo-font-size-l)")
            .set("margin", "0");

        addToDrawer(tabsComponent);
        addToNavbar(toggle, header);
    }

    public void addTab(Tab... tabs) {
        tabsComponent.add(tabs);
    }

    public static Tab createTab(VaadinIcon viewIcon, String name, Class<? extends Component> route) {
        RouterLink link = new RouterLink();

        Icon icon = null;
        if (viewIcon != null) {
            icon = viewIcon.create();
            icon.getStyle()
                .set("box-sizing", "border-box")
                .set("margin-inline-end", "var(--lumo-space-m)")
                .set("margin-inline-start", "var(--lumo-space-xs)")
                .set("padding", "var(--lumo-space-xs)");

            link.add(icon, new Span(name));
        }

        link.getElement().getThemeList().add("dark");
        link.setRoute(route);

        Tab tab = new Tab(link);
        ComponentUtil.setData(tab, Class.class, route);
        return tab;
    }

    public void addButton(Button... buttons) {
        for (Button button : buttons) {
            this.buttons.add(button);
        }
        tabsComponent.add(buttons);
    }

    public Button[] getButtons() {
        Button[] buttons = new Button[this.buttons.size()];
        return this.buttons.toArray(buttons);
    }

    // private Optional<Tab> getTabForComponent(Component parent) {
    //     return tabsComponent.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(parent.getClass())).findFirst().map(Tab.class::cast);
    // }

    // private String getCurrentPageTitle() {
    //     PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
    //     return title == null ? "" : title.value();
    // }
}