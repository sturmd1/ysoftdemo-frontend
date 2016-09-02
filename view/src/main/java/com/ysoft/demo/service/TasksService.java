package com.ysoft.demo.service;

import com.ysoft.demo.domain.Task;

import java.util.List;

/**
 * Created by dsturm on 9/1/2016.
 */
public interface TasksService {
    List<Task> getTasks(String userName);

    Task saveTask(String userName, Task task);

    void deleteTask(String userName, Long taskId);

    void updateTask(String userName, Task task);
}
