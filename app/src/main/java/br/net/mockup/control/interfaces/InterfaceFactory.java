package br.net.mockup.control.interfaces;

/**
 * Created by Thiago on 15/10/2015.
 */
public class InterfaceFactory {

    public static final int PHOTO_CHOICE_DIALOG = 1;

    public static Interface create(int name){
        switch (name){
            case PHOTO_CHOICE_DIALOG:
                return ChoicePhoto.getInstance();
        }

        return new NoInterface();
    }
}
