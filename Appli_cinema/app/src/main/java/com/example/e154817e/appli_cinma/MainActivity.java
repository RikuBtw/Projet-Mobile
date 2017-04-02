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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //Size
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
        //Tri
        final Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
        List<String> categories2 = new ArrayList<String>();
        categories2.add("Titre ascendants");
        categories2.add("Titre descendants");
        categories2.add("Date récentes");
        categories2.add("Date anciennes");
        categories2.add("Note ascendantes");
        categories2.add("Note descendantes");
        ArrayAdapter<String> categorieAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categories2);
        categorieAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(categorieAdapter2);
        //Bouton
        final EditText searchBar = (EditText)findViewById(R.id.editText);
        Button bouton = (Button)findViewById(R.id.button);
        bouton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                searchBar.setImeOptions(EditorInfo.IME_ACTION_DONE);
                rechercher(v, searchBar.getText().toString(), Integer.parseInt(spinner.getSelectedItem().toString()), spinner2.getSelectedItem().toString());
            }

        });

    }
    //Recherche
    private void rechercher(View v, String tag, final int max, final String tri){
        System.out.println(tag);
        RequestQueue queue = Volley.newRequestQueue(this);
        tag = tag.replaceAll(" ", "+");
        JsonObjectRequest stringRequest =
                new JsonObjectRequest(
                        Request.Method.POST,
                        "https://api.themoviedb.org/3/search/movie?api_key=b6c9ea5303eb45ffcc0ee1a092b448c9&query="+tag+"&language=fr-FR", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                System.out.println(response);
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
                                    JSONObject object = response;
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
                                            switch (tri) {
                                                case "Titre ascendants":
                                                    Collections.sort(listeFilm, new NameComparator());
                                                    break;
                                                case "Titre descendants":
                                                    NameComparator comparator = new NameComparator();
                                                    Collections.sort(listeFilm, Collections.reverseOrder(comparator));
                                                    break;
                                                case "Date anciennes":
                                                    Collections.sort(listeFilm, new DateComparator());
                                                    break;
                                                case "Date récentes":
                                                    DateComparator comparator2 = new DateComparator();
                                                    Collections.sort(listeFilm, Collections.<Film>reverseOrder(comparator2));
                                                    break;
                                                case "Note ascendantes":
                                                    Collections.sort(listeFilm, new NoteComparator());
                                                    break;
                                                case "Note descendantes":
                                                    NoteComparator comparator3 = new NoteComparator();
                                                    Collections.sort(listeFilm, Collections.<Film>reverseOrder(comparator3));
                                                    break;
                                                default:
                                                    break;
                                            }
                                        }
                                    }
                                }catch (org.json.JSONException js){
                                    js.getMessage();
                                }
                                TextView visu = (TextView) findViewById(R.id.visu);
                                visu.setVisibility(View.GONE);
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

                                TextView visu = (TextView) findViewById(R.id.visu);
                                visu.setVisibility(View.VISIBLE);
                                visu.setText("Pas de résultat");
                            }});

        queue.add(stringRequest);
    }
}