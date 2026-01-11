package com.todo;

import com.todo.model.Task;
import com.todo.service.TaskManager;

import java.util.Scanner;

public class App {
    private final TaskManager taskManager = new TaskManager();
    public void run(String[] args) {
        System.out.println("Starting TODO CLI...");
        Scanner sc=new Scanner(System.in);
        System.out.println("1. Add a task\n2. Read all tasks\n3. Edit your tasks\n4.Delete a task\n5. Exit\n Choose your option: ");
        int input=sc.nextInt();

        switch (input)
        {
            case 1:
                System.out.println("Enter your task: ");
                String title= sc.nextLine();
                Task t1=Task.create(title);
                taskManager.addTask(t1);
                break;
            case 2:
                for(Task task : taskManager.getAllTasks())
                {
                    System.out.println(task);
                }
                break;

            case 3:
                break;

            case 4:
                System.out.println("Enter id of task to remove: ");
                for (Task task : taskManager.getAllTasks())
                {
                    System.out.println(task);
                }
                String id=sc.nextLine();
                taskManager.removeTask(id);
                System.out.println("Task removed successfully");
                break;

            case 5:
                System.exit(0);
                break;

            default:
                System.out.println("Invalid input please select a valid option");
        }

    }

    public static void main(String[] args) {
        new App().run(args);
    }
}
