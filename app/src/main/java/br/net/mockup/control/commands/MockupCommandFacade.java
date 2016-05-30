package br.net.mockup.control.commands;

import br.net.mockup.model.commands.Command;
import br.net.mockup.control.factory.Factory;
import br.net.mockup.model.invoke.MockupInvoke;
import br.net.mockup.control.factory.MockupFactoryManager;

/**
 * Facade para comandos principais da aplicacao
 * Created by Thiago on 30/10/2015.
 */
public abstract class MockupCommandFacade {
    private Command command;
    private Factory factory;

    public MockupCommandFacade factory(MockupFactoryManager mockupFactoryManager){
        factory = mockupFactoryManager.getMainCommandFactory();
        return this;
    }

    public MockupCommandFacade command(String command) {
        this.command = (Command) factory.create(command);
        return this;
    }

    public Command getCommand() {
        return command;
    }

    public MockupCommandFacade execute(){
        MockupInvoke mockupInvoke = MockupInvoke.getInstance();
        mockupInvoke.setCommand(command);
        mockupInvoke.execute();
        return this;
    }
}
