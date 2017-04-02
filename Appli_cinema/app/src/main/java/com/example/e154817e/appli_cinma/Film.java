package com.example.e154817e.appli_cinma;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * Created by Riku on 29/03/2017.
 */

public class Film implements Parcelable {

    private String id;
    private String titre;
    private String resume;
    private String titreOriginal;
    private String date;
    private String affiche;
    private String note;


    public Film(String filmId, String filmTitre, String filmOriginal, String filmResume, String filmDate, String filmAffiche, String filmNote){
        this.id = filmId;
        this.titre = filmTitre;
        this.resume = filmResume;
        this.titreOriginal = filmOriginal;
        this.date = filmDate;
        this.affiche = filmAffiche;
        this.note = filmNote;
    }

    public Film(Parcel in){
        this.id = in.readString();
        this.titre = in.readString();;
        this.resume = in.readString();
        this.titreOriginal = in.readString();
        this.date = in.readString();
        this.affiche = in.readString();
        this.note = in.readString();
    }

    public String getId(){
        return this.id;
    }
    public String getTitre(){
        return this.titre;
    }
    public String getTitreOriginal(){
        return this.titreOriginal;
    }
    public String getDate(){
        return this.date;
    }
    public String getNote(){
        return this.note;
    }
    public String getResume() {
        return this.resume;
    }
    public String getAffiche() {
        return this.affiche;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.titre);
        dest.writeString(this.resume);
        dest.writeString(this.titreOriginal);
        dest.writeString(this.date);
        dest.writeString(this.affiche);
        dest.writeString(this.note);
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Film createFromParcel(Parcel in) {
            return new Film(in);
        }

        public Film[] newArray(int size) {
            return new Film[size];
        }
    };

}
