package com.example.e154817e.appli_cinma;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Variables
        final ArrayList<String> listItems=new ArrayList<String>();

        //Elements
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        List<String> categories = new ArrayList<String>();
        ListView lv = (ListView)findViewById(R.id.listView);
        Button bouton = (Button)findViewById(R.id.button);
        final EditText searchBar = (EditText)findViewById(R.id.editText);

        //Initialisation
        categories.add("10");
        categories.add("20");
        categories.add("30");
        categories.add("40");
        categories.add("50");

        //Adapter
        ArrayAdapter<String> categorieAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        final ArrayAdapter<String> listeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);

        // Drop down layout style - list view with radio button
        categorieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //SetAdapter
        spinner.setAdapter(categorieAdapter);
            //lv.setListAdapter(listeAdapter);

        //Listeners
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              // On selecting a spinner item
              String item = parent.getItemAtPosition(position).toString();

              // Showing selected spinner item
              Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
              // TODO Auto-generated method stub
            }
        });

        bouton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                listItems.add(searchBar.getText().toString());
                listeAdapter.notifyDataSetChanged();

                System.out.println(listItems);
            }
        });


    }



}

