package com.example.jeremy.listes;

import android.app.LauncherActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ListView musique;
    private ArrayList<HashMap<String,String>> listItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fetch the listView created in the XML file Main
        musique = (ListView) findViewById(R.id.musique);

        //HashMap with all elements (Image,Description...) inside the list
        listItem = new ArrayList<>();

        //We declare an Hashmap which is equivalent to one element
        HashMap<String,String> element = new HashMap<>();

        //the goal here is to add in the HashMap all the attributes whiches define one element
        element.put("titre", "Smell like a teen spirit");
        element.put("description", "Nirvana");
        element.put("image", String.valueOf(R.mipmap.ic_nirvana));

        //Once the element created, we add it inside the ArrayList listItem
        listItem.add(element);

        //We have to make these operations a lot of times for creating another element
        element = new HashMap<>();
        element.put("titre", "MasterWorm");
        element.put("description", "Pang!");
        element.put("image", String.valueOf(R.mipmap.ic_pang));
        listItem.add(element);

        element = new HashMap<>();
        element.put("titre", "Who knows");
        element.put("description", "Protoje");
        element.put("image", String.valueOf(R.mipmap.ic_protoje));
        listItem.add(element);

        element = new HashMap<>();
        element.put("titre", "Four Walls");
        element.put("description", "While She Sleeps");
        element.put("image", String.valueOf(R.mipmap.ic_whileshesleeps));
        listItem.add(element);

        element = new HashMap<>();
        element.put("titre", "Highscore");
        element.put("description", "Panda Eyes");
        element.put("image", String.valueOf(R.mipmap.ic_pandaeyes));
        listItem.add(element);

        //creation of a SimpleAdapter
        SimpleAdapter musique_schedule = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.affichageitem,
                new String[]{"image", "titre", "description"},
                new int[]{R.id.img, R.id.titre, R.id.description}

                );

        musique.setAdapter(musique_schedule);

        musique.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> hashmapselected = (HashMap<String, String>) musique.getItemAtPosition(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Music selected");
                alert.setIcon(Integer.parseInt(hashmapselected.get("image")));
                alert.setMessage("You have selected : " + hashmapselected.get("titre").toString() + " by " + hashmapselected.get("description").toString());
                alert.setPositiveButton("Got It", null);
                alert.show();
            }
        });


    }
}
