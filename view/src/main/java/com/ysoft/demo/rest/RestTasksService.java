package com.ysoft.demo.rest;

import com.ysoft.demo.domain.Task;
import com.ysoft.demo.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Created by dsturm on 9/1/2016.
 * Uses Spring's RestTemplate to communicate with the backend REST API
 */
@Service
public class RestTasksService implements TasksService {

    @Value("${api.url}")
    private String apiUrl;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public List<Task> getTasks(String userName) {
        Task[] tasks = restTemplate.getForObject(getUrl(userName), Task[].class);
        return Arrays.asList(tasks);
    }

    @Override
    public Task saveTask(String userName, Task task) {
        return restTemplate.postForObject(getUrl(userName), task, Task.class);
    }

    @Override
    public void deleteTask(String userName, Long taskId) {
        restTemplate.delete(getUrl(userName) + "/" + taskId);
    }

    @Override
    public void updateTask(String userName, Task task) {
        restTemplate.put(getUrl(userName), task);
    }

    private String getUrl(String userName) {
        return String.format(apiUrl, userName);
    }

}
