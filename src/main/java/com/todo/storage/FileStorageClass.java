package com.todo.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class FileStorageClass
{
    public void createNewTaskList(String listName) throws IOException
    {
        Path dir= Paths.get("taskslists");
        Files.createDirectories(dir);
        Path file= dir.resolve(listName + ".txt");
        Files.createFile(file);
    }

    public Path getTaskListPath(String listName) throws IOException
    {
        Path dir = Paths.get("taskslists");
        Files.createDirectories(dir);
        Path path = dir.resolve(listName + ".txt");
        return path;
    }

    public List<String> readAllLines(String listName) throws IOException
    {
        Path path = Paths.get("taskslists", listName + ".txt");
        List<String> lines = Files.readAllLines(path);
        return lines;
    }
    public void writeAllLines(Path file, List<String> lines) throws IOException
    {
        Files.write(file, lines);
    }

    public List<Path> listTaskLists() throws IOException
    {
        Path storageDir = Paths.get("taskslists");

        if (!Files.exists(storageDir))
        {
            Files.createDirectories(storageDir);
        }

        try(Stream<Path> stream = Files.list(storageDir))
        {
            return stream.filter(Files :: isRegularFile).toList();
        }
    }
    public void deleteTaskList(Path file) throws IOException
    {
        Files.deleteIfExists(file);
    }
}

//Create a new task list: A method to create a new file for a task list. done
//Switch between task lists: A method to set the current task list file.
//Add a task to the current task list: A method to append a task to the current file.
//Read tasks from the current task list: A method to read all tasks from the current file.
//Delete or update tasks in the current task list: Methods to modify tasks in the current file.