package com.example.e154817e.appli_cinma;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FilmViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent i = getIntent();
        Film f= i.getParcelableExtra("film");

        TextView nom = (TextView)findViewById(R.id.Titre);
        nom.setText(f.getTitre());

        TextView resume = (TextView)findViewById(R.id.Resume);
        resume.setText(f.getResume());

        ImageView affiche = (ImageView)findViewById(R.id.Affiche);
        Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w185/"+f.getAffiche()).into(affiche);
    }
}
