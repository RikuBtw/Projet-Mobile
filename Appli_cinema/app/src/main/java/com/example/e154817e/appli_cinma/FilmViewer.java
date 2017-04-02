package com.example.e154817e.appli_cinma;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

public class FilmViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent i = getIntent();
        Film f = i.getParcelableExtra("film");

        TextView nom = (TextView) findViewById(R.id.Titre);
        nom.setText(f.getTitre());

        if(f.getTitre().equals(f.getTitreOriginal())) {
            TextView origin = (TextView) findViewById(R.id.Original);
            origin.setVisibility(View.GONE);
        }else{
            TextView origin = (TextView) findViewById(R.id.Original);
            origin.setText("("+f.getTitreOriginal()+")");
            origin.setVisibility(View.VISIBLE);
        }

        TextView date = (TextView) findViewById(R.id.Date);
        date.setText(f.getDate());

        RatingBar note = (RatingBar) findViewById(R.id.Note);
        note.setRating(Float.valueOf(f.getNote())/2);

        TextView resume = (TextView) findViewById(R.id.Resume);
        resume.setMovementMethod(new ScrollingMovementMethod());
        resume.setText(f.getResume());

        ImageView affiche = (ImageView) findViewById(R.id.Affiche);
        Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w185/" + f.getAffiche()).into(affiche);


        //Get Casting
        final String[] cast = {""};
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest stringRequest =
                new JsonObjectRequest(
                        Request.Method.GET,
                        "https://api.themoviedb.org/3/movie/" + f.getId() + "/credits?api_key=b6c9ea5303eb45ffcc0ee1a092b448c9", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONObject object = response;
                                    for (int i = 0; i < 3  &&  i < object.getJSONArray("cast").length(); i++) {
                                            cast[0] += object.getJSONArray("cast").getJSONObject(i).getString("name")+", ";
                                    }
                                    cast[0] += "...";
                                    TextView casting = (TextView) findViewById(R.id.Casting);
                                    casting.setText(cast[0]);
                                } catch (org.json.JSONException js) {
                                    js.getMessage();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
        queue.add(stringRequest);
        //Get realisateur
        final String[] crew = {""};
        RequestQueue queue2 = Volley.newRequestQueue(this);
        JsonObjectRequest stringRequest2 =
                new JsonObjectRequest(
                        Request.Method.GET,
                        "https://api.themoviedb.org/3/movie/" + f.getId() + "/credits?api_key=b6c9ea5303eb45ffcc0ee1a092b448c9", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONObject object = response;
                                    for (int i = 0; i < 1  &&  i < object.getJSONArray("crew").length(); i++) {
                                        crew[0] += object.getJSONArray("crew").getJSONObject(i).getString("name");
                                    }
                                    TextView directeur = (TextView) findViewById(R.id.Realisateur);
                                    directeur.setText(crew[0]);
                                } catch (org.json.JSONException js) {
                                    js.getMessage();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
        queue2.add(stringRequest2);
        //GET Tag
        final String[] tag = new String[1];
        tag[0] ="";
        RequestQueue queue3 = Volley.newRequestQueue(this);
        JsonObjectRequest stringRequest3 =
                new JsonObjectRequest(
                        Request.Method.GET,
                        "https://api.themoviedb.org/3/movie/" + f.getId() + "?api_key=b6c9ea5303eb45ffcc0ee1a092b448c9", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONObject object = response;
                                    for (int i = 0; i < 3  &&  i < object.getJSONArray("genres").length(); i++) {
                                        tag[0] += object.getJSONArray("genres").getJSONObject(i).getString("name");
                                        if(i != 2 && i != object.getJSONArray("genres").length()-1 ) {
                                            tag[0] += ", ";
                                        }
                                    }
                                    if(Math.min(tag[0].length(), 20) == 20) {
                                        tag[0] = tag[0].substring(0, Math.min(tag[0].length(), 20));
                                        tag[0] += "...";
                                    }
                                    TextView tags = (TextView) findViewById(R.id.tags);
                                    tags.setText(tag[0]);
                                } catch (org.json.JSONException js) {
                                    js.getMessage();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
        queue3.add(stringRequest3);
        //GET duree
        final String[] duree = new String[1];
        tag[0] ="";
        RequestQueue queue4 = Volley.newRequestQueue(this);
        JsonObjectRequest stringRequest4 =
                new JsonObjectRequest(
                        Request.Method.GET,
                        "https://api.themoviedb.org/3/movie/" + f.getId() + "?api_key=b6c9ea5303eb45ffcc0ee1a092b448c9", null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    JSONObject object = response;
                                    duree[0] = object.getString("runtime");
                                    System.out.println(duree[0]);
                                    if (duree[0].equals(null)){
                                        duree[0] = "0";
                                    }
                                    int hours = Integer.parseInt(duree[0]) / 60;
                                    int minutes = Integer.parseInt(duree[0])% 60;
                                    if(minutes<10){
                                        duree[0] = ""+hours+"h0"+minutes;
                                    }else{
                                        duree[0] = ""+hours+"h"+minutes;
                                    }
                                    TextView time = (TextView) findViewById(R.id.duree);
                                    time.setText(duree[0]);
                                } catch (org.json.JSONException js) {
                                    js.getMessage();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
        queue4.add(stringRequest4);
    }
}

