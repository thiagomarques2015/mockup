package br.net.mockup.control.task;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import static br.net.mockup.control.util.Printlog.erro;

/**
 * Created by Thiago on 26/06/2015.
 */
class MyRejectedExecutionHandelerImpl implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
        erro(runnable.toString() + " : Foi rejeitada ! ");
    }
}
