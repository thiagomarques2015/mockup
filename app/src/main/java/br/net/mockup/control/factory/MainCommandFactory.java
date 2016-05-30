package br.net.mockup.control.factory;

import android.content.Context;

import br.net.mockup.control.commands.MockupMainCommand;

/**
 * Created by Thiago on 30/10/2015.
 */
class MainCommandFactory extends Factory<MockupMainCommand> {

    public MainCommandFactory(Context context, String file, MockupFactoryManager mockupFactoryManager) {
        super(context, file, mockupFactoryManager);
    }
}
