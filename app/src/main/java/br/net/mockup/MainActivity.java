package br.net.mockup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.net.mockup.model.fragment.BaseFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Carrega os fragments da tab
        ArrayList<Map<String, Fragment>> fragments = new ArrayList<>();
//        Fragment camera = new Fragment();
        fragments.add(createItem("Eifles", new BaseFragment()));
        fragments.add(createItem("Top", new BaseFragment()));
        fragments.add(createItem("Recebido", new BaseFragment()));
        fragments.add(createItem("Enviado", new BaseFragment()));
        fragments.add(createItem("Amigos", new BaseFragment()));
        // Carrega os icones dos fragments
        ArrayList<Integer> icons = new ArrayList<>();
        /*
            Exemplo de incones
            icons.add(R.drawable.ic_photo_camera);
            icons.add(R.drawable.ic_action_achievement);
            icons.add(R.drawable.ic_archive);
            icons.add(R.drawable.ic_redo);
            icons.add(R.drawable.ic_mood);
        */
        // Implementar com padrao de projetos a tab
//        new Tab(fragments).with(getSupportFragmentManager(), this).use(getResources()).icons(icons).into(tabHost, pager).init();
    }

    /**
     * Cria um fragment para ser inserido na Tab
     * @param key titulo do fragment
     * @param fragment view do fragment
     * @return item par-valor com o fragment
     */
    private Map<String, Fragment> createItem(String key, Fragment fragment){
        Map<String, Fragment> item = new HashMap<>();
        item.put(key, fragment);
        return item;
    }
}
