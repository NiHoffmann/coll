package org.hbrs.se2.project.components.Profile;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;

import org.hbrs.se2.project.classes.User.Student;
import org.hbrs.se2.project.components.Form.StudentForm;
import org.hbrs.se2.project.control.SessionControl;
import org.hbrs.se2.project.control.UserControl;

/**
 * Component to show a students profile
 */
public class StudentProfile extends Profile<Student> {
    public final StudentForm form;

    public StudentProfile(Student user) {
        header.setText("Student profile of " + user.getFirstname() + " " + user.getLastname());

        form = new StudentForm(user);
        form.setReadonly(true);

        int localUserId = SessionControl.myData.getUserID();

        if (localUserId == user.getUserID()) {
            setEditable(true);
            
            editButton.addClickListener(event -> {
                if (isEditing) {
                    try {
                        form.binder.writeBean(user);

                        UserControl.updateUser(user);
    
                        setEditing(true);
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

        add(form);
    }

    public StudentProfile(int userID) {
        this(UserControl.getStudentInfo(userID));
    }
}
