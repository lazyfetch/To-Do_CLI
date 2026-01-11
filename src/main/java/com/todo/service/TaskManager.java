package com.todo.service;
import com.todo.model.Task;
import java.util.ArrayList;
import java.util.*;

public class TaskManager
{
    private List<Task> tasks = new ArrayList<>();

    public void addTask(Task task)
    {
        tasks.add(task);
    }

    public boolean removeTask(String id)
    {
        tasks.removeIf(task -> task.id().equals(id));
        return true;
    }

    public List<Task> getAllTasks()
    {
        return tasks;
    }

}
