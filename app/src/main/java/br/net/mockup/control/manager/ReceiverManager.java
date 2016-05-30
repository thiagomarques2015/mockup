package br.net.mockup.control.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Gerenciador de mensagens broadcast enviadas pelo app
 * Created by Thiago on 30/10/2015.
 */
class ReceiverManager {

    private static BroadcastReceiver mRegistrationBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String command = intent.getStringExtra("command");

           /* // Cria o comando
            MockupCommandFacade c = MockupCommandFacade.getInstance().factory(Mockup.getInstance().getMockupFactoryManager()).command(command);
*//*            // Seta o contexto
            c.getCommand().setContext(context);*//*

            switch (command){
                // Implementa o case dos comandos
            }

            // Executa o comando
            c.execute();*/
        }
    };

    public static BroadcastReceiver getReceiver(){
        return mRegistrationBroadcastReceiver;
    }
}
