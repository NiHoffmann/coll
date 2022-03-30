package org.hbrs.se2.project.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;

// TODO import styles/scrollbar.css
public class FloatingDiv extends Div {
    public static void makeFloat(Component component) {
        component.getElement().getStyle()
            .set("position", "fixed")
            .set("top", "50%")
            .set("left", "50%")
            .set("-webkit-transform", "translate(-50%, -50%)")
            .set("transform", "translate(-50%, -50%)")
            .set("z-index", "10")
            .set("overflow-y", "scroll");
    }
    
    public FloatingDiv() {
        makeFloat(this);

        getElement().getThemeList().add("dark");

        getElement().getStyle()
            .set("outline-style", "solid")
            .set("outline-width", "2px")
            .set("outline-color", "var(--lumo-primary-color)")
            .set("border-radius", "10px")
            .set("padding-left", "10px")
            .set("padding-right", "10px")
            .set("padding-bottom", "10px")
            .set("background-color", "hsl(214, 40%, 25%)");
    }
}
