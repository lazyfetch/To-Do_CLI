package com.todo.util;

import com.todo.service.TaskManager;

public class AutoSaveThread extends Thread
{

    private TaskManager manager;

    public AutoSaveThread(TaskManager manager)
    {
        this.manager = manager;
    }

    public void run()
    {
        while(true)
        {
            try
            {
                Thread.sleep(5000);
                if(manager.checkCurrentFilePath())
                {
                    manager.saveTaskList();
                }
            }
            catch (InterruptedException e)
            {
                break;
            }
            catch(Exception e)
            {
                System.out.println("Saved failed: " + e.getMessage());
            }
        }
    }
}
