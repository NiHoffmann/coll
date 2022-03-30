package org.hbrs.se2.project.components;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;

/**
 * Extended class of Notification which makes it easier to use with String
 */
public class Toast extends Notification {
    public static Toast open(String message) {
        Toast toast = new Toast(message);

        return toast;
    }


    public Toast() {
        this.setPosition(Position.TOP_CENTER);
        this.setDuration(5000);
    }

    public Toast(String message) {
        this();
        this.setText(message);
    }

    /**
     * See com.vaadin.flow.component.notification.NotificationVariant
     * @param type
     */
    public void setType(String type) {
        this.addThemeVariants(NotificationVariant.valueOf(type));
    }

    public void setRounding(String rounding) {
        this.getElement().getStyle().set("border-radius", rounding);
    }

    public void setTransparency(float alpha) {
        this.getElement().getStyle().set("opacity", String.valueOf(alpha));
    }
}
