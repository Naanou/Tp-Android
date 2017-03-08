package guevara.valentin.tp2_android;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private ListView contact;
    private HashMap<String, String> renseignement;
    private ArrayList<HashMap<String,String>> listItem;
    private SimpleAdapter contact_schedule;

    //We declare an Hashmap which is equivalent to one element
    private HashMap<String,String> element;

    Button b_contact_adding;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("liste", listItem);
    }

    private void restore(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            listItem = (ArrayList<HashMap<String,String>>) savedInstanceState.getSerializable("liste");
            contact_schedule.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        b_contact_adding = (Button) findViewById(R.id.button);

        //fetch the listView created in the XML file Main
        contact = (ListView) findViewById(R.id.contact);

        //HashMap with all elements (Image,Description...) inside the list
        listItem = new ArrayList<>();


        //We have to make these operations a lot of times for creating another element
        element = new HashMap<>();
        element.put("nom", "Velien Fanny");
        element.put("numero", "0609098149");
        element.put("naissance", "02/01/1997");
        element.put("mail", "fanny.velien@live.com");
        element.put("sexe", "F");
        element.put("image", String.valueOf(R.mipmap.ic_default_profile));
        listItem.add(element);

        //creation of a SimpleAdapter
        contact_schedule = new SimpleAdapter(this.getBaseContext(), listItem, R.layout.affichageitem,
                new String[]{"image", "nom", "numero"},
                new int[]{R.id.img, R.id.titre, R.id.description}

        );

        contact.setAdapter(contact_schedule);

        contact.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final HashMap<String, String> hashmapselected = (HashMap<String, String>) contact.getItemAtPosition(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Contact selectionne");
                alert.setIcon(Integer.parseInt(hashmapselected.get("image")));
                alert.setMessage("Vous avez choisi : " + hashmapselected.get("nom").toString() + " " + hashmapselected.get("numero").toString());
                alert.setPositiveButton("Appeler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", hashmapselected.get("numero"), null));
                        startActivity(intent);
                    }
                });
                alert.setNegativeButton("detail", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent myIntentDetail = new Intent(MainActivity.this, ActivityB.class);
                        myIntentDetail.putExtra("contactSelected", hashmapselected);
                        startActivity(myIntentDetail);
                    }
                });
                alert.show();
            }
        });

        contact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int contactASupprimer = position;
                final HashMap<String, String> hashmapselected = (HashMap<String, String>) contact.getItemAtPosition(position);
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Supprimer Contact");
                alert.setMessage("Voulez-vous reellement supprimer le contact : " + hashmapselected.get("nom").toString());
                alert.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listItem.remove(contactASupprimer);
                        contact_schedule.notifyDataSetChanged();
                    }
                });
                alert.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // ne rien faire
                    }
                });
                alert.show();
                return true;
            }
        });

        b_contact_adding.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this, newContact.class);
                startActivityForResult(myIntent, 1);
            }
        });

      //  onSaveInstanceState();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            System.out.println("adding");
            if (resultCode == RESULT_OK) {
                renseignement = (HashMap<String, String>) data.getSerializableExtra("contact");
                element = new HashMap<>();
                element.put("nom", renseignement.get("nom") + " " + renseignement.get("prenom"));
                element.put("numero", renseignement.get("numero"));
                element.put("naissance", renseignement.get("dateNaiss"));
                element.put("mail", renseignement.get("mail"));
                element.put("sexe", renseignement.get("sexe"));
                element.put("image", String.valueOf(R.mipmap.ic_default_profile));
                listItem.add(element);
                contact_schedule.notifyDataSetChanged(); //add the new contact
            }
            if (resultCode == RESULT_CANCELED) {
                // Write your code if there's no result
            }
        }
    }
}
