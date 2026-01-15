package com.todo.model;
import java.time.LocalDate;
import java.util.UUID;

public class Task
{
        private String id;
        private String title;
        private boolean isCompleted;
        private LocalDate createdAt;

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";

    public Task (String title)
    {
        this.id=UUID.randomUUID().toString();
        this.title=title;
        this.isCompleted=false;
        this.createdAt=LocalDate.now();
    }

    public Task(String id,String title, boolean isCompleted, LocalDate createdAt)
    {
        this.id=id;
        this.title=title;
        this.isCompleted=isCompleted;
        this.createdAt=createdAt;
    }

    public String getId()
    {
        return id;
    }

    public String getTitle()
    {
        return title;
    }

    public boolean getIsCompleted()
    {
        return isCompleted;
    }

    public LocalDate getCreatedAt()
    {
        return createdAt;
    }

    public void updateTitle(String title)
    {
        this.title=title;
    }

    public void updateIsCompleted(boolean isCompleted)
    {
        this.isCompleted=isCompleted;
    }


    public String toString()
    {
        if(isCompleted)
        {
            return ANSI_GREEN + "[COMPLETED] " + title + " (Created: " + createdAt + ")" + ANSI_RESET;
        }
        else
        {
            return ANSI_RED + "[PENDING]   " + title + " (Created: " + createdAt + ")" + ANSI_RESET;
        }
    }
}