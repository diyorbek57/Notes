package com.ayizor.notes.helper;

import androidx.annotation.NonNull;

import com.ayizor.notes.Note;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class RealmHelper {
    Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    //WRITE
    public void save(final Note note) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {

                Note n = realm.copyToRealm(note);

            }
        });

    }

    //READ
    public ArrayList<Note> retrieve() {

        ArrayList<Note> spacecraftNames = new ArrayList<>();
        RealmResults<Note> notes = realm.where(Note.class).findAll();

        for (Note n : notes) {
            spacecraftNames.add(new Note(n.getId(), n.getTitle(), n.getDescription(), n.getDate(), n.getTime()));
        }

        return spacecraftNames;
    }
}
