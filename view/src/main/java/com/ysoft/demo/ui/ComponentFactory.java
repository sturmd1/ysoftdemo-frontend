package com.ysoft.demo.ui;

import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by dsturm on 9/1/2016.
 */
public class ComponentFactory {
    public static Label createBigLabel(String text) {
        Label label = new Label(text);
        label.addStyleName("h1");
        label.addStyleName("colored");
        return label;
    }

    public static Button createPrimaryButton(String caption) {
        Button button =  new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_PRIMARY);
        return button;
    }

    public static Button createSecondaryButton(String caption) {
        Button button =  new Button(caption);
        return button;
    }

    public static Button createFriendlyButton(String caption) {
        Button button =  new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_FRIENDLY);
        return button;
    }

    public static Button createDangerButton(String caption) {
        Button button =  new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_DANGER);
        return button;
    }
}
