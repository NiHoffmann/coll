package org.hbrs.se2.project.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import org.hbrs.se2.project.components.Toast;
import org.hbrs.se2.project.control.ApplicationControl;

public class CreateOffer extends Div {
    private String jobType;
    private String description;

    public CreateOffer(int companyID) {
        setSizeFull();
        getElement().getThemeList().add("dark");

        H2 header = new H2("Create a job offer");
        TextField jobTypeField = new TextField("Job Type");
        TextArea descriptionField = new TextArea("Description");
        Button createButton = new Button("Submit");

        header.setText("Create a job offer");

        jobTypeField.addValueChangeListener(e -> {
            jobType = e.getValue();
        });

        descriptionField.setMaxLength(2000);
        descriptionField.addValueChangeListener(e -> {
            String text = e.getValue();

            description = text;
            e.getSource().setHelperText(text.length() + "/" + descriptionField.getMaxLength());
        });

        createButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS, ButtonVariant.LUMO_PRIMARY);
        createButton.addClickListener(clickEvent -> {
            try {
                if (jobType.length() == 0) {
                    throw new Exception("Please enter a job position");
                }
                if (description.length() == 0) {
                    throw new Exception("Please enter a job description");
                }

                ApplicationControl.createJobOffer(companyID, jobType, description);
                
            } catch (Exception e) {
                if (e.getMessage() != null) {
                    Toast.open(e.getMessage()).setType("LUMO_ERROR");
                }
            }

            Toast.open("Created job offer").setType("LUMO_SUCCESS");
        });

        add(header, jobTypeField, descriptionField, createButton);
    }

    public String getJobPosition() {
        return jobType;
    }

    public void setJobPosition(String jobType) {
        this.jobType = jobType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}