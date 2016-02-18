package br.net.mockup.control.task;

/**
 * Executor de tarefas assincronas
 * Created by Thiago on 25/06/2015.
 */
public class AsyncTaskFacade {

    private static AsyncTaskFacade ourInstance = new AsyncTaskFacade();
    // * Tarefa que sera executada
    private AsyncTask task;
    // * Gerenciador de tarefas assincronas
    private TaskManager taskManager;

    private AsyncTaskFacade() {
        taskManager = TaskManager.getInstance();
    }

    public synchronized static AsyncTaskFacade getInstance() {
        return ourInstance;
    }

    /**
     * Configura os parametros para criar uma nova tarefa
     * @param task Tarefa que sera executada
     * @param nameTask Nome da tarefa
     */
    public AsyncTaskFacade create(AsyncTask task, String nameTask){
        this.task = task;
        taskManager.setName(nameTask);
        taskManager.setCurrentTask(task);
        return this;
    }

    /**
     * Cancela uma tarefa que esteja aberta
     * @param nameTask nome da tarefa
     * @return Instancia de AsyncTaskFacade
     */
    public AsyncTaskFacade stop(String nameTask){
        taskManager.remove(nameTask);
        return this;
    }


    /**
     * Configura os parametros para criar uma nova tarefa utilizando conexao
     * @param delegate Feedback para receber mensagens apos a tarefa ser executada
     */
    public AsyncTaskFacade delegate(AsyncTask.TaskCompleted delegate){
        if(task == null) throw new IllegalArgumentException("Falta criar a tarefa para delegar o evento de callback");
        task.setTaskCompleted(delegate);
        return this;
    }

    // Executa a tarefa
    public void execute(){
        if(taskManager == null) throw new IllegalArgumentException("Falta criar a tarefa para ser executada");
        taskManager.execute();
    }
}
