package com.example.dudilugasi.doit.common;

import java.util.Date;

/**
 * class task item represents a task
 */
public class TaskItem {

    private long id;
    private Date created;
    private String category;
    private String priority;
    private String location;
    private Date dueTime;
    private String assignee;
    private String status;
    private String accept;
    private String imageUrl;
    private String taskName;

    public void setCreated(Date created) {
        this.created = created;
    }

    public TaskItem() {
        this.created = new Date();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String toString() {
        return "TaskItem{" +
                "id=" + id +
                ", created=" + created +
                ", category='" + category + '\'' +
                ", priority='" + priority + '\'' +
                ", location='" + location + '\'' +
                ", dueTime=" + dueTime +
                ", assignee='" + assignee + '\'' +
                ", status='" + status + '\'' +
                ", accept='" + accept + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", taskName='" + taskName + '\'' +
                '}';
    }
}
