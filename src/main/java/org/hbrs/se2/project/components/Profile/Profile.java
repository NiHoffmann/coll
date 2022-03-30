package org.hbrs.se2.project.components.Profile;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.upload.Upload;

import org.hbrs.se2.project.classes.User.User;
import org.hbrs.se2.project.control.UserControl;
//Class for setting the basis of the profile layout
public class Profile<T extends User> extends Div {
    T user;
    boolean isEditing = false;

    final H2 header;
    final Button editButton;
    final Button deleteButton;
    final Image avatarImage;
    final Upload editAvatarUpload;

    protected Profile() {
        header = new H2("Profile");

        editButton = new Button("Edit");
        editButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        editButton.getElement().getStyle()
            .set("margin-left", "10px")
            .set("float", "right");

        deleteButton = new Button("Delete Account", event -> {
            int userID = user.getUserID();

            UI ui = this.getUI().get();
            ui.getSession().close();

            UserControl.logout();
            try {
                UserControl.deleteUser(userID);
            } catch (Exception e) {
            }

            ui.getPage().setLocation("/");
        });
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        avatarImage = new Image("https://dummyimage.com/600x400/000/fff", "DummyImage");
        avatarImage.setMaxHeight("200px");
        avatarImage.setMaxWidth("200px");

        Button editAvatarUploadButton = new Button("Change Picture");
        editAvatarUploadButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        editAvatarUpload = new Upload();
        editAvatarUpload.setDropAllowed(false);
        editAvatarUpload.setUploadButton(editAvatarUploadButton);
        editAvatarUpload.setVisible(false);

        add(editButton, header, avatarImage, editAvatarUpload);

        editButton.setVisible(false);
        deleteButton.setVisible(false);
    }

    protected void setEditable(boolean state) {
        editButton.setVisible(state);

        if (state) {
            add(deleteButton);
        }
        else {
            remove(deleteButton);
        }
    }

    protected void setEditing(boolean state) {
        isEditing = state;

        deleteButton.setVisible(state);
        editAvatarUpload.setVisible(!state);

        if (state) {
            editButton.setText("Save");
        }
        else {
            editButton.setText("Edit");
        }
    }
}
