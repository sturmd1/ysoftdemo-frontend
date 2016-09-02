package com.ysoft.demo.ui;


import com.vaadin.ui.*;
import com.ysoft.demo.screens.TasksScreen;

/**
 * Created by dsturm on 9/2/2016.
 */
public class ChangeUserDialog extends CustomComponent {

    TextField user = new TextField("User");
    Button btnGo = ComponentFactory.createFriendlyButton("Go");
    HorizontalLayout layout = new HorizontalLayout(user, btnGo);
    Window window = new Window("Change User", this);

    public ChangeUserDialog() {
        layout.setComponentAlignment(btnGo, Alignment.BOTTOM_CENTER);
        layout.setSpacing(true);
        layout.setMargin(true);
        btnGo.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                window.close();
                UI.getCurrent().getNavigator().navigateTo(TasksScreen.SCREEN_NAME + "/" + user.getValue());
            }
        });
        user.setValue("");
        window.center();
        setCompositionRoot(layout);
    }

    public void show() {
        UI.getCurrent().addWindow(window);
    }
}
