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

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.grid.HeightMode;
import com.vaadin.ui.*;
import com.ysoft.demo.domain.Task;
import com.ysoft.demo.events.TaskSubmitEvent;
import com.ysoft.demo.service.TasksService;
import com.ysoft.demo.ui.ChangeUserDialog;
import com.ysoft.demo.ui.ComponentFactory;
import com.ysoft.demo.ui.TaskSubmitHandler;
import com.ysoft.demo.ui.TaskEditForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestClientException;
import ru.xpoft.vaadin.VaadinView;

import javax.annotation.PostConstruct;
import java.util.List;

@org.springframework.stereotype.Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@VaadinView(TasksScreen.SCREEN_NAME)
public class TasksScreen extends AbstractScreen implements TaskSubmitHandler {

    public static final String SCREEN_NAME = "tasks";
    public static final String DEFAULT_USER = "/david";

    @Autowired
    private TasksService tasksService;

    private String userName;
    private VerticalLayout layout = new VerticalLayout();

    private BeanItemContainer<Task> dataContainer = new BeanItemContainer<>(Task.class);
    private Grid taskGrid = new Grid(dataContainer);

    private Button btnAdd = ComponentFactory.createPrimaryButton("Add");
    private Button btnEdit = ComponentFactory.createSecondaryButton("Edit");
    private Button btnDelete = ComponentFactory.createDangerButton("Delete");
    private Button btnDone = ComponentFactory.createFriendlyButton("Done");
    private Button btnChangeUser = ComponentFactory.createSecondaryButton("Change User");
    private TaskEditForm taskEditForm = new TaskEditForm();

    @PostConstruct
    private void init() {
        setPrimaryStyleName("valo-content");
        addStyleName("v-scrollable");
        initializeGrid();
        layout.addComponent(ComponentFactory.createBigLabel("Tasks"));
        layout.addComponent(btnChangeUser);
        layout.addComponent(taskGrid);
        layout.addComponent(addButtons());
        initButtons();
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setSizeFull();
        layout.setExpandRatio(taskGrid, 1);
        taskEditForm.addListener(TaskSubmitEvent.class, this, TaskSubmitHandler.HANDLE_SUBMIT_METHOD);
    }

    private void initializeGrid() {
        dataContainer.removeContainerProperty("id");
        taskGrid.setWidth("100%");
        taskGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        taskGrid.setColumnOrder(Task.PROPERTY_TITLE,
                                Task.PROPERTY_DESCRIPTION,
                                Task.PROPERTY_PRIORITY,
                                Task.PROPERTY_DONE);

        taskGrid.getColumn(Task.PROPERTY_DESCRIPTION).setExpandRatio(1);
        taskGrid.getColumn(Task.PROPERTY_TITLE).setMinimumWidth(200);
        taskGrid.getColumn(Task.PROPERTY_PRIORITY).setMinimumWidth(200);
        taskGrid.getColumn(Task.PROPERTY_DONE).setMinimumWidth(200);

        taskGrid.setHeightMode(HeightMode.ROW);
        taskGrid.setHeightUndefined();

        taskGrid.addSelectionListener(new SelectionEvent.SelectionListener() {
            @Override
            public void select(SelectionEvent selectionEvent) {
                updateBtnVisibility(taskGrid.getSelectedRow() != null);
            }
        });
    }

    private void updateBtnVisibility(boolean visibility) {
        btnEdit.setVisible(visibility);
        btnDelete.setVisible(visibility);
        btnDone.setVisible(visibility);
    }

    private Component addButtons() {
        HorizontalLayout layout = new HorizontalLayout(btnAdd, btnEdit, btnDelete, btnDone);
        updateBtnVisibility(taskGrid.getSelectedRow() != null);
        layout.setSpacing(true);
        return layout;
    }

    private void initButtons() {
        btnEdit.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                taskEditForm.setBean((Task) taskGrid.getSelectedRow());
                taskEditForm.show();
            }
        });

        btnAdd.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Task task = new Task();
                taskEditForm.setBean(task);
                taskEditForm.show();
            }
        });

        btnDelete.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Task task = (Task) taskGrid.getSelectedRow();
                tasksService.deleteTask(userName, task.getId());
                dataContainer.removeItem(task);
                taskGrid.clearSortOrder();
            }
        });

        btnDone.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Task task = (Task) taskGrid.getSelectedRow();
                task.setDone(true);
                tasksService.updateTask(userName, task);
                taskGrid.clearSortOrder();
            }
        });

        btnChangeUser.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                new ChangeUserDialog().show();
            }
        });
    }

    @Override
    public Component getContent() {
        return layout;
    }

    public void displayTasks() {
        try {
            List<Task> tasks = tasksService.getTasks(userName);
            dataContainer.addAll(tasks);
        } catch (RestClientException e) {
            Notification.show(e.getMessage(), Notification.Type.ERROR_MESSAGE);
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        if (viewChangeEvent.getParameters() == "") {
            UI.getCurrent().getNavigator().navigateTo(TasksScreen.SCREEN_NAME + DEFAULT_USER);
        } else {
            userName = viewChangeEvent.getParameters();
            displayTasks();
        }
    }

    @Override
    public void handleTaskSubmit(TaskSubmitEvent event) {
        Task task = event.getSource();
        if (task.getId() == null) {
            //new task
            Task savedTask = tasksService.saveTask(userName, task);
            dataContainer.addBean(savedTask);
        } else {
            //task update
            tasksService.updateTask(userName, task);
        }
        //this is a hack how to repaint the grid - bug in Vaadin
        taskGrid.clearSortOrder();
    }
}
