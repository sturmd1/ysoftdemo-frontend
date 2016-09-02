package com.ysoft.demo.domain;

/**
 * Created by dsturm on 8/31/2016.
 * Jackson automatically serialize and deserialize this POJO to JSON
 * that the backend consumes
 */
public class Task {

    public static final String PROPERTY_TITLE = "title";
    public static final String PROPERTY_DESCRIPTION = "description";
    public static final String PROPERTY_PRIORITY = "priority";
    public static final String PROPERTY_DONE = "done";

    private Long id;
    private String title;
    private String description;
    private  String priority = "MEDIUM";
    private boolean done;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
