package com.ysoft.demo;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.ysoft.demo.screens.ErrorScreen;
import com.ysoft.demo.screens.TasksScreen;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.xpoft.vaadin.DiscoveryNavigator;

@Component("MyUI")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Theme("poc-theme-default-valo")
public class MyUI extends UI {

    @Override
    protected void init(final VaadinRequest vaadinRequest) {
        final DiscoveryNavigator navigator = new DiscoveryNavigator(this, this);
        navigator.addBeanView(TasksScreen.SCREEN_NAME, TasksScreen.class);
        navigator.setErrorView(ErrorScreen.class);
        setNavigator(navigator);
        navigator.navigateTo(TasksScreen.SCREEN_NAME);
    }

}
