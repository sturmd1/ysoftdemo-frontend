/*
* Copyright (c) 2015 by Casenet, LLC
*
* This file is protected by Federal Copyright Law, with all rights
* reserved. No part of this file may be reproduced, stored in a
* retrieval system, translated, transcribed, or transmitted, in any
* form, or by any means manual, electric, electronic, mechanical,
* electro-magnetic, chemical, optical, or otherwise, without prior
* explicit written permission from Casenet, LLC.
*/
package com.ysoft.demo.screens;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

public class ErrorScreen extends CssLayout implements View {

    @Override
    public void enter(final ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        addComponent(new Label("Sorry but there is nothing here. "));
        final Button btnBack = new Button("Back...", new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent clickEvent) {
                UI.getCurrent().getNavigator().navigateTo(TasksScreen.SCREEN_NAME);
            }
        });
        btnBack.addStyleName(ValoTheme.BUTTON_LINK);
        addComponent(btnBack);
    }
}
