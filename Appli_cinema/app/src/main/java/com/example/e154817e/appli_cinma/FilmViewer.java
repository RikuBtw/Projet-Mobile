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
            origin.setText(f.getTitreOriginal());
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

        final String[] cast = {""};
        //Get Casting/Directeur
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
                                    //Variable contenant les acteurs pincipaux
                                    for (int i = 0; i < 3; i++) {
                                        System.out.println(object.getJSONArray("results").getJSONObject(i));
                                        if (object.getJSONArray("cast").getJSONObject(i) != null) {
                                            cast[0] += object.getJSONArray("cast").getJSONObject(i).getString("name")+", ";
                                        }
                                    }
                                    cast[0] += "...";

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
        TextView realisateur = (TextView) findViewById(R.id.Realisateur);
        realisateur.setText(cast[0]);

    }
}

