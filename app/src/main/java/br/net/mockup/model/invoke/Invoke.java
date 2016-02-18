package br.net.mockup.model.invoke;

import br.net.mockup.model.commands.Command;

/**
 * Created by Thiago on 30/10/2015.
 */
public class Invoke {
    private static Invoke ourInstance = new Invoke();
    private Command command;

    public static Invoke getInstance() {
        return ourInstance;
    }

    private Invoke() {
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void execute(){
        if(command == null) return;
        command.execute();
    }
}
