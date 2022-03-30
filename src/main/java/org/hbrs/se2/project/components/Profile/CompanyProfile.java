package org.hbrs.se2.project.components.Profile;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;

import org.hbrs.se2.project.classes.User.Company;
import org.hbrs.se2.project.components.Form.CompanyForm;
import org.hbrs.se2.project.control.SessionControl;
import org.hbrs.se2.project.control.UserControl;

/**
 * Component to show a companys profile
 */
public class CompanyProfile extends Profile<Company> {
    public final CompanyForm form;

    public CompanyProfile(Company user) {
        this.user = user;
        header.setText("Company profile of " + user.getName());

        form = new CompanyForm(user);
        form.setReadonly(true);
        add(form);

        int localUserId = SessionControl.myData.getUserID();
        
        if (localUserId == user.getUserID()) {
            setEditable(true);

            editButton.addClickListener(event -> {
                if (isEditing) {
                    try {
                        form.binder.writeBean(user);
    
                        UserControl.updateUser(user);
    
                        setEditing(false);
                        form.setReadonly(true);
    
                        Notification note = Notification.show("Saved details");
                        note.setPosition(Position.TOP_CENTER);
                        note.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                        note.setDuration(3000);
                    } catch (Exception e) {
                        Notification note = Notification.show(e.getMessage());
                        note.setPosition(Position.TOP_CENTER);
                        note.addThemeVariants(NotificationVariant.LUMO_ERROR);
                        note.setDuration(3000);
                    }
                }
                else {
                    setEditing(true);
                    form.setReadonly(false);
                }
            });
        }
        
        
    }

    public CompanyProfile(int userID) {
        this(UserControl.getCompanyInfo(userID));
    }
}
