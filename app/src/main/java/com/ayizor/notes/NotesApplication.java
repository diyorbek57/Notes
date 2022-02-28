package com.ayizor.notes;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class NotesApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);

        // on below line we are setting realm configuration

    }
}
