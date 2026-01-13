package com.todo.service;
import com.todo.model.Task;
import com.todo.storage.FileStorageClass;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.*;

public class TaskManager
{
    FileStorageClass storage= new FileStorageClass();
    private Path currentFilePath;
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
    public void convertToTasks(String listName) throws IOException
    {
        List<String> lines= storage.readAllLines(listName);
        for (String line : lines)
        {
            String[] parts= line.split(";");

            String id=parts[0];
            String title=parts[1].replace("\\;",";");

            boolean isCompleted = Boolean.parseBoolean(parts[2]);
            LocalDate createdAt = LocalDate.parse(parts[3]);

            Task loadedTask = new Task(id,title,isCompleted,createdAt);
            tasks.add(loadedTask);
        }
    }
    
    public void loadTaskList(String listName) throws IOException
    {
        this.currentFilePath = storage.getTaskListPath(listName);
        this.tasks.clear();

        convertToTasks(listName);
    }

    public void saveTaskList() throws IOException
    {
        List<String> lines = new ArrayList<>();
        for (Task task : tasks)
        {
            String safeTitle = task.title().replace(";", "\\;");
            String line = task.id() + ";" + safeTitle + ";" + task.isCompleted() + ";" + task.createdAt();
            lines.add(line);
        }

        storage.writeAllLines(currentFilePath, lines);


    }


}
