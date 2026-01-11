package com.todo.model;
import java.time.LocalDate;
import java.util.UUID;

public record Task(
        String id,
        String title,
        boolean isCompleted,
        LocalDate createdAt
)
{
    public static Task create(String title)
    {
        return new Task(
                UUID.randomUUID().toString(),
                title,
                false,
                LocalDate.now()
        );
    }
}