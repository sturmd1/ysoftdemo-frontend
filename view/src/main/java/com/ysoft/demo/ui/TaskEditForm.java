package com.ysoft.demo.ui;


import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.ui.*;
import com.ysoft.demo.domain.Task;
import com.ysoft.demo.events.TaskSubmitEvent;

import java.util.Arrays;

/**
 * Created by dsturm on 9/1/2016.
 */
public class TaskEditForm extends CustomComponent {

    private FormLayout layout =  new FormLayout();
    private static final String[] priorities = {"LOW", "MEDIUM", "HIGH"};

    @PropertyId(Task.PROPERTY_TITLE)
    private TextField title = new TextField("Title");

    @PropertyId(Task.PROPERTY_DESCRIPTION)
    private TextField description = new TextField("Description");

    @PropertyId(Task.PROPERTY_PRIORITY)
    private ComboBox priority = new ComboBox("Priority", Arrays.asList(priorities));

    private Button btnSubmit = ComponentFactory.createPrimaryButton("Submit");

    private BeanFieldGroup<Task> binder = new BeanFieldGroup<>(Task.class);

    private Window window = new Window();

    public TaskEditForm() {
        priority.setNullSelectionAllowed(false);
        title.setNullRepresentation("");
        description.setNullRepresentation("");
        title.setRequired(true);
        description.setRequired(true);
        layout.addComponent(title);
        layout.addComponent(description);
        layout.addComponent(priority);
        layout.addComponent(btnSubmit);
        layout.setMargin(true);
        window.center();
        window.setWidth("600px");
        title.setWidth("400px");
        description.setWidth("400px");
        priority.setWidth("400px");

        btnSubmit.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                try {
                    binder.commit();
                    fireEvent(new TaskSubmitEvent(getBean()));
                    window.close();
                } catch (FieldGroup.CommitException e) {
                    Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
                }
            }
        });

        setCompositionRoot(layout);
        window.setContent(this);
    }

    public void show() {
        UI.getCurrent().addWindow(window);
    }

    public void setBean(Task bean) {
        binder.setItemDataSource(bean);
        binder.bindMemberFields(this);
    }

    public Task getBean() {
        return binder.getItemDataSource().getBean();
    }
}
