package br.net.mockup.model.invoke;

import br.net.mockup.model.commands.Command;

/**
 * Created by Thiago on 30/10/2015.
 */
public class MockupInvoke {
    private static MockupInvoke ourInstance = new MockupInvoke();
    private Command command;

    public static MockupInvoke getInstance() {
        return ourInstance;
    }

    private MockupInvoke() {
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void execute(){
        if(command == null) return;
        command.execute();
    }
}
