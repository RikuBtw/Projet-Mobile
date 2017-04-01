package com.example.e154817e.appli_cinma;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FilmAdapter extends ArrayAdapter<Film> {
    public FilmAdapter(Activity context, ArrayList<Film> items) {
        super(context, R.layout.listview, items);
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        row=inflater.inflate(R.layout.listview, null);
        Film f = getItem(position);
        TextView nom = (TextView)row.findViewById(R.id.Titre);
        nom.setText(f.getTitre());
        TextView original = (TextView)row.findViewById(R.id.Original);
        original.setText("("+f.getTitreOriginal()+")");
        TextView date = (TextView)row.findViewById(R.id.Date);
        date.setText(f.getDate());
        TextView note = (TextView)row.findViewById(R.id.Note);
        note.setText(f.getNote()+"/10");
        ImageView affiche = (ImageView)row.findViewById(R.id.Affiche);
        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185/"+f.getAffiche()).into(affiche);
        return(row);
    }}