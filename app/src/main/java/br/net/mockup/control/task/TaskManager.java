package br.net.mockup.control.task;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import br.net.mockup.Mockup;

import static br.net.mockup.control.util.Printlog.debug;
import static br.net.mockup.control.util.Printlog.erro;
import static br.net.mockup.control.util.Printlog.info;

/**
 * Gerenciador de tarefas 
 * Created by Thiago on 25/06/2015.
 */
public class TaskManager{

    // Sets the amount of time an idle thread waits before terminating
    private static final int KEEP_ALIVE_TIME = 1;
    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
    // Piscina de tarefas
    private final ThreadPoolExecutor mDecodeThreadPool;
    // Pilha de tarefas armazenadas em cache
    private HashMap<String, Object> tasks;
    // Nome da tarefa
    private String name;
    // Ultima tarefa executada
    private Object currentTask;
    // Lista de tarefas em execucao
    private HashMap<String, AsyncTask> running;
    // Set monitor active
    private boolean monitor;

    private static final TaskManager instance = new TaskManager();

    private TaskManager() {

        monitor = Mockup.getInstance().isMonitorTasks();

        tasks = new HashMap<>();
        running = new HashMap<>();

        // Criando o gerenciador de monitoramento das tarefas
        int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
        mDecodeThreadPool = new ThreadPoolExecutor(
                NUMBER_OF_CORES * 2,       // Initial pool size
                NUMBER_OF_CORES * 2,       // Max pool size
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                new ArrayBlockingQueue<Runnable>(10),
                new MyRejectedExecutionHandelerImpl());
        mDecodeThreadPool.allowCoreThreadTimeOut(true);

        if(monitor){
            // Iniciando o monitor de tarefas assincronas
            Thread monitor = new Thread(new MyMonitorThread(mDecodeThreadPool));
            monitor.setDaemon(true);
            monitor.start();
        }
    }

    public static TaskManager getInstance() {
        return instance;
    }

    public void execute() {
        if(currentTask == null){ erro("Nao foi possivel executar a nova tarefa em " + getClass().getName()); return;  }

        if(running.containsKey(name)){
            try {
                debug(String.format("=> A tarefa '%s' ja esta em execucao desde { %s }", name, running.get(name).getCreated().toString()));
            } catch (Exception e) {
                e.printStackTrace();
                debug(String.format("=> A tarefa '%s' ja esta em execucao", name));
            }
            return;
        }

        /**
         * Adiciona na pilha a nova tarefa se tiver um nome
         */
        if(name != null){
            if(!tasks.containsKey(name))
                tasks.put(name, currentTask);
        }

        // Configura a tarefa e armazena seu nome
        AsyncTask task = (AsyncTask) currentTask;
        task.setName(name);
        task.setOnFinishedTask(onFinishedTask);

        // Adiciona na lista de execucao
        running.put(name, task);

        //debug("Tipo de objeto " + currentTask.getClass().getName());
        //debug(String.format("Existe tarefas ? %s ...", !mDecodeThreadPool.isShutdown()));

        try {
            // Executa a tarefa aberta
            mDecodeThreadPool.execute(task);

            debug(String.format("Iniciando a tarefa %s ...", name));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TaskManager setName(String name) {
        this.name = name;
        return this;
    }

    public TaskManager setCurrentTask(Object currentTask) {
        this.currentTask = currentTask;
        return this;
    }

    /**
     * Recupera a tarefa da pilha
     * @param task tarefa que sera executada
     * @return instancia da tarefa
     */
    public Object get(String task){
        return tasks.get(task);
    }

    public TaskManager remove(String task){

        info("=> Removendo a tarefa '" + task + "' da lista de execucao");
        // Remove da lista de execucao a tarefa
        running.remove(task);

        return this;
    }

    private OnFinishedTask onFinishedTask = new OnFinishedTask() {
        @Override
        public void onFinishTask(String name) {
            remove(name);
            info("Total de tarefas ativas : " + running.size());
        }
    };
}
