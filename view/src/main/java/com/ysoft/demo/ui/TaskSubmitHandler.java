package com.ysoft.demo.ui;

import com.vaadin.util.ReflectTools;
import com.ysoft.demo.events.TaskSubmitEvent;

import java.lang.reflect.Method;

/**
 * Created by dsturm on 9/1/2016.
 */
public interface TaskSubmitHandler {
    public static final Method HANDLE_SUBMIT_METHOD = ReflectTools.findMethod(TaskSubmitHandler.class, "handleTaskSubmit", TaskSubmitEvent.class);
    void handleTaskSubmit(TaskSubmitEvent event);
}
