package com.ayizor.notes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ayizor.notes.activity.EditActivity;
import com.ayizor.notes.helper.RealmHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {
    private Realm realm;
    private FloatingActionButton actionButton;
    private RecyclerView recyclerView;
    private NotesAdapter adapter;
    private ArrayList<Note> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inits();
    }

    @Override
    protected void onStart() {
        super.onStart();
        refreshAdapter();
    }

    private void inits() {
        RealmConfiguration config =
                new RealmConfiguration.Builder()
                        .allowWritesOnUiThread(true)
                        .deleteRealmIfMigrationNeeded()
                        .build();
        Realm.setDefaultConfiguration(config);
        recyclerView = findViewById(R.id.recycler_view);
        actionButton = findViewById(R.id.action_button);
        recyclerView.setHasFixedSize(true);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && actionButton.isShown())
                    actionButton.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE)
                    actionButton.show();
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        realm = Realm.getDefaultInstance();
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, RecyclerView.VERTICAL, false));
        refreshAdapter();
    }

    private void refreshAdapter() {
//RETRIEVE
        RealmHelper helper = new RealmHelper(realm);
        notesList = helper.retrieve();

        //BIND
        adapter = new NotesAdapter(this, notesList);
        recyclerView.setAdapter(adapter);
    }
}