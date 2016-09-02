package com.ysoft.demo.events;

import com.ysoft.demo.domain.Task;

import java.util.EventObject;

/**
 * Created by dsturm on 9/1/2016.
 */
public class TaskSubmitEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public TaskSubmitEvent(Task source) {
        super(source);
    }

    @Override
    public Task getSource() {
        //narrowing the type...
        return (Task) super.getSource();
    }
}
