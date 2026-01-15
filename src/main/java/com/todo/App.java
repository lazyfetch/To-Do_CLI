package com.todo;

import com.todo.model.Task;
import com.todo.service.TaskManager;
import com.todo.storage.FileStorageClass;
import com.todo.util.AutoSaveThread;

import java.io.IOException;
import java.util.List;
import java.nio.file.Path;

import java.util.Scanner;

public class App
{
    private final TaskManager taskManager = new TaskManager();
    public void run(String[] args) {
        System.out.println("Starting TODO CLI...");

        AutoSaveThread saveThread= new AutoSaveThread(taskManager);
        saveThread.setDaemon(true); // means the thread dies when the main app finishes.

        saveThread.start();


        Scanner sc=new Scanner(System.in);

        int input = 0;
        do
        {
            System.out.println("1. List all task lists\n2. Choose Task List\n3. Show all tasks\n4.Add a task\n5. Delete a Task\n6. Update a task\n7. Exit\nChoose your option: ");

            input=sc.nextInt();

            FileStorageClass storage = new FileStorageClass();

            switch (input)
            {
                case 1:
                    try
                    {
                        List <Path> taskLists= storage.listTaskLists();
                        for(Path path: taskLists)
                        {
                            System.out.println(path);
                        }
                    }
                    catch (IOException e)
                    {
                        System.out.println("Error listing the task lists: " + e.getMessage());
                    }
                    break;

                case 2:
                    try
                    {
                        List <Path> taskLists= storage.listTaskLists();
                        int i=1;
                        for(Path path : taskLists)
                        {
                            System.out.println(i+ "." + " " +path+"\n");
                            i++;
                        }
                        System.out.println("Choose your task list");

                        int taskListChoice=sc.nextInt();
                        sc.nextLine();

                        if (taskListChoice < 1 || taskListChoice > taskLists.size())
                        {
                            System.out.println("Invalid choice.");
                            break;
                        }

                        Path chosenPath = taskLists.get(taskListChoice - 1);

                        String fileName=chosenPath.getFileName().toString();
                        String listName=fileName.replaceFirst("\\.txt","");

                        taskManager.loadTaskList(listName);
                        System.out.println("Loaded task list: " + listName);
                    }
                    catch (IOException e)
                    {
                        System.out.println("Error loading the task lists: " + e.getMessage());
                    }
                    break;

                case 3:

                    if (!taskManager.checkCurrentFilePath())
                    {
                        System.out.println("You haven't selected a task list, choose that first");
                    }
                    else
                    {
                        List<Task> taskList= taskManager.getAllTasks();
                        if(taskList.isEmpty())
                        {
                            System.out.println("No tasks yet!");
                        }
                        else
                        {
                            System.out.println("\n========= TODO LIST =========");


                            System.out.println("\n--- PENDING ---");
                            boolean hasPending = false;

                            for(Task task:taskList)
                            {
                                if(!task.getIsCompleted())
                                {
                                    System.out.println(task);
                                    hasPending = true;
                                }
                            }
                            if(!hasPending)
                            {
                                System.out.println("(None)");
                            }

                            System.out.println("\n--- COMPLETED ---");

                            boolean hasCompleted = false;
                            for(Task task : taskList)
                            {
                                if(task.getIsCompleted())
                                {
                                    System.out.println(task);
                                    hasCompleted = true;
                                }
                            }
                            if(!hasCompleted)
                            {
                                System.out.println("(None)");
                            }

                            System.out.println("\n=============================");


                        }
                    }
                    break;

                case 4:
                    if (!taskManager.checkCurrentFilePath())
                    {
                        System.out.println("You haven't selected a task list, choose that first");
                    }
                    else
                    {
                        sc.nextLine();

                        System.out.println("Enter your task: ");
                        String task  = sc.nextLine();

                        if(task.trim().isEmpty())
                        {
                            System.out.println("Task cannot be empty");
                        }
                        else
                        {
                            Task newTask=new Task(task);
                            taskManager.addTask(newTask);
                            System.out.println("Task added!");
                        }

                    }

                    break;

                case 5:
                    if (!taskManager.checkCurrentFilePath())
                    {
                        System.out.println("You haven't selected a task list, choose that first");
                    }
                    else
                    {
                        List<Task> taskList= taskManager.getAllTasks();
                        if(taskList.isEmpty())
                        {
                            System.out.println("No tasks to delete");
                        }
                        else
                        {
                            sc.nextLine();
                            int i=1;
                            for(Task task:taskList)
                            {
                                System.out.println(i+ ". " + task);
                                i++;
                            }
                            System.out.println("Enter the number of the task to delete: ");
                            int num=sc.nextInt();

                            if (num < 1 || num > taskList.size())
                            {
                                System.out.println("Invalid task number.");
                            }
                            else
                            {
                                Task taskToRemove=taskList.get(num-1);

                                String id = taskToRemove.getId();
                                taskManager.removeTask(id);
                            }

                        }
                    }
                    break;
                case 6:
                    if (!taskManager.checkCurrentFilePath())
                    {
                        System.out.println("You haven't selected a task list, choose that first");
                    }
                    else
                    {
                        List<Task> taskList= taskManager.getAllTasks();
                        if(taskList.isEmpty())
                        {
                            System.out.println("No tasks to update");
                        }
                        else
                        {
                            sc.nextLine();
                            int i=1;
                            for(Task task:taskList)
                            {
                                System.out.println(i+ ". " + task);
                                i++;
                            }
                            System.out.println("Enter the number of the task you want to update: ");
                            int num=sc.nextInt();

                            if (num < 1 || num > taskList.size())
                            {
                                System.out.println("Invalid task number.");
                            }
                            else
                            {
                                sc.nextLine();
                                System.out.println("1. Title\n2. Completion Status\n What do you want to update?");
                                int choice=sc.nextInt();

                                if(choice==1)
                                {
                                    Task taskToUpdate=taskList.get(num-1);

                                    System.out.println("Enter new title: ");
                                    sc.nextLine();
                                    String newTitle = sc.nextLine();

                                    taskToUpdate.updateTitle(newTitle);
                                    System.out.println("Task updated successfully!");
                                }
                                else if (choice==2)
                                {
                                    Task taskToUpdate=taskList.get(num-1);
                                    boolean isCompleted=taskToUpdate.getIsCompleted();

                                    if(isCompleted)
                                    {
                                        taskToUpdate.updateIsCompleted(false);
                                    }
                                    else
                                    {
                                        taskToUpdate.updateIsCompleted(true);
                                    }
                                    System.out.println("Task updated successfully!");
                                }
                            }

                        }
                    }
                    break;

                case 7:
                    System.out.println("Bye!!!");
                    System.exit(0);

                default:
                    System.out.println("Invalid input please select a valid option");
            }
        } while (input != 7);
    }

    public static void main(String[] args)
    {
        new App().run(args);
    }
}
