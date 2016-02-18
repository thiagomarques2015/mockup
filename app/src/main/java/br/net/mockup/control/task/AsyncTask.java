package br.net.mockup.control.task;

import android.os.Process;
import android.support.annotation.Nullable;

import java.util.Calendar;
import java.util.Date;

import static br.net.mockup.control.util.Printlog.debug;

/**
 * Tarefa assincrona com execucao em background
 * Created by Thiago on 25/06/2015.
 */
public abstract class AsyncTask implements Runnable {

    private String name;
    private Date created;
    private TaskCompleted taskCompleted;
    private OnFinishedTask onFinishedTask;

    public AsyncTask() {
        name = Calendar.getInstance().getTime().getTime() + "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOnFinishedTask(OnFinishedTask onFinishedTask) {
        this.onFinishedTask = onFinishedTask;
    }

    @Override
    public void run() {
        created = Calendar.getInstance().getTime();

        try {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        } catch (IllegalArgumentException | SecurityException e) {
            e.printStackTrace();
        }
    }

    public void setTaskCompleted(TaskCompleted taskCompleted) {
        this.taskCompleted = taskCompleted;
    }

    /**
     * Finaliza a task enviando uma mensagem e o retorno
     * @param msg mensagem enviada quando a tarefa foi finalizada
     */
    public void finish(String msg, @Nullable Object object){

        try {
            if(taskCompleted != null){
                debug(String.format("Enviando o aviso '%s' de uma atividade concluida", msg));
                taskCompleted.onTaskCompleted(msg, object);
            }else{
                debug(String.format("Nao existe um callback para a tarefa '%s'", msg));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(onFinishedTask != null)
            onFinishedTask.onFinishTask(name);
    }

    public Date getCreated() {
        return created;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Toda operacao assincrona que for finalizada envia uma mensagem
     * sobre a operacao que foi concluida
     */
    public interface TaskCompleted {
        void onTaskCompleted(String msg, Object object);
    }
}
