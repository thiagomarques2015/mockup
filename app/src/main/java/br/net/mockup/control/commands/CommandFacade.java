package br.net.mockup.control.commands;

import br.net.mockup.model.commands.Command;
import br.net.mockup.model.factory.Factory;
import br.net.mockup.model.invoke.Invoke;
import br.net.mockup.model.manager.FactoryManager;

/**
 * Facade para comandos principais da aplicacao
 * Created by Thiago on 30/10/2015.
 */
public abstract class CommandFacade {
    private Command command;
    private Factory factory;

    public CommandFacade factory(FactoryManager factoryManager){
        factory = factoryManager.getMainCommandFactory();
        return this;
    }

    public CommandFacade command(String command) {
        this.command = (Command) factory.create(command);
        return this;
    }

    public Command getCommand() {
        return command;
    }

    public CommandFacade execute(){
        Invoke invoke = Invoke.getInstance();
        invoke.setCommand(command);
        invoke.execute();
        return this;
    }
}
