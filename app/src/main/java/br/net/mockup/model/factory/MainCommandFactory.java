package br.net.mockup.model.factory;

import android.content.Context;

import br.net.mockup.model.commands.MainCommand;
import br.net.mockup.model.manager.FactoryManager;

/**
 * Created by Thiago on 30/10/2015.
 */
public class MainCommandFactory extends Factory<MainCommand> {

    public MainCommandFactory(Context context, String file, FactoryManager factoryManager) {
        super(context, file, factoryManager);
    }
}
