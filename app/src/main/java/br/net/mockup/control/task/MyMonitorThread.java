package br.net.mockup.control.task;

import java.util.concurrent.ThreadPoolExecutor;

import static br.net.mockup.control.util.Printlog.debug;

/**
 * Created by Thiago on 26/06/2015.
 */
public class MyMonitorThread implements Runnable {
    ThreadPoolExecutor executor;

    public MyMonitorThread(ThreadPoolExecutor executor)
    {
        this.executor = executor;
    }

    @Override
    public void run()
    {
        try
        {
            do
            {
                debug(
                        String.format("[monitor] [%d/%d] Active: %d, Completed: %d, Task: %d, isShutdown: %s, isTerminated: %s",
                                this.executor.getPoolSize(),
                                this.executor.getCorePoolSize(),
                                this.executor.getActiveCount(),
                                this.executor.getCompletedTaskCount(),
                                this.executor.getTaskCount(),
                                this.executor.isShutdown(),
                                this.executor.isTerminated()));
                Thread.sleep(5000);
            }
            while (true);
        }
        catch (Exception e)
        {
        }
    }
}
