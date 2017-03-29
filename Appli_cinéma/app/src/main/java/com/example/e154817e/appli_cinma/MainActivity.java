package com.example.e154817e.appli_cinma;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //Spinner
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        List<String> categories = new ArrayList<String>();
        categories.add("10");
        categories.add("20");
        categories.add("30");
        categories.add("40");
        categories.add("50");
        ArrayAdapter<String> categorieAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        categorieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(categorieAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              String item = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
              // TODO Auto-generated method stub
            }
        });
        //Bouton
        final EditText searchBar = (EditText)findViewById(R.id.editText);
        Button bouton = (Button)findViewById(R.id.button);
        bouton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                searchBar.setImeOptions(EditorInfo.IME_ACTION_DONE);
                rechercher(v, searchBar.getText().toString(), Integer.parseInt(spinner.getSelectedItem().toString()));
            }

        });

    }
    //Recherche
    private void rechercher(View v, String tag, final int max){
        System.out.println(tag);
        RequestQueue queue = Volley.newRequestQueue(this);
        tag = tag.replaceAll(" ", "+");
        StringRequest stringRequest =
                new StringRequest(
                        Request.Method.POST,
                        "https://api.themoviedb.org/3/search/movie?api_key=b6c9ea5303eb45ffcc0ee1a092b448c9&query="+tag,
                        new Response.Listener<String>() {
                            public void onResponse(String response) {

                                final ArrayList<String> listItems=new ArrayList<String>();
                                final ListView lv = (ListView)findViewById(R.id.listView);
                                final ArrayAdapter<String> listeAdapter = new ArrayAdapter<String>((MainActivity.this).getBaseContext(), android.R.layout.simple_list_item_1, listItems);
                                lv.setAdapter(listeAdapter);

                                /* Ce listener permet le fonctionnement du click sur la liste */
                                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent intent=new Intent(view.getContext(),FilmViewer.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.putExtra("film", (Parcelable) lv.getItemAtPosition(position));
                                        view.getContext().startActivity(intent);
                                    }
                                });
                                ArrayList<Film> listeFilm = new ArrayList<Film>();
                                try {
                                    JSONObject object = (JSONObject) new JSONTokener(response).nextValue();
                                   for(int i=0; i<max;i++) {
                                       System.out.println(object.getJSONArray("results").getJSONObject(i));
                                       if(object.getJSONArray("results").getJSONObject(i)!=null) {
                                           String id = object.getJSONArray("results").getJSONObject(i).getString("id");
                                           String titre = object.getJSONArray("results").getJSONObject(i).getString("title");
                                           String originTtitre = object.getJSONArray("results").getJSONObject(i).getString("original_title");
                                           String resume = object.getJSONArray("results").getJSONObject(i).getString("overview");
                                           String date = object.getJSONArray("results").getJSONObject(i).getString("release_date");
                                           String affiche = object.getJSONArray("results").getJSONObject(i).getString("poster_path");
                                           String note = object.getJSONArray("results").getJSONObject(i).getString("vote_average");
                                           Film film = new Film(id, titre, originTtitre, resume, date, affiche, note);
                                           System.out.println(film.toString());
                                           listeFilm.add(film);
                                       }
                                   }



                                    TextView visu = (TextView)
                                            findViewById(R.id.visu);

                                }catch (org.json.JSONException js){
                                    js.getMessage();
                                }
                                FilmAdapter filmAdapter = new FilmAdapter((MainActivity.this), listeFilm);
                                System.out.println(filmAdapter.toString());
                                lv.setAdapter(filmAdapter);
                                filmAdapter.notifyDataSetChanged();
                            }},
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                final ArrayList<String> listItems=new ArrayList<String>();
                                final ListView lv = (ListView)findViewById(R.id.listView);
                                final ArrayAdapter<String> listeAdapter = new ArrayAdapter<String>((MainActivity.this).getBaseContext(), android.R.layout.simple_list_item_1, listItems);
                                lv.setAdapter(listeAdapter);

                                TextView visu = (TextView)
                                        findViewById(R.id.visu);
                                visu.setText("Pas de résultat");
                            }});

        queue.add(stringRequest);
    }
}