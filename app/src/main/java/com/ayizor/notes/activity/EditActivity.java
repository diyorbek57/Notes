package com.ayizor.notes.activity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.ayizor.notes.Note;
import com.ayizor.notes.R;
import com.ayizor.notes.helper.RealmHelper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class EditActivity extends AppCompatActivity {
    private EditText desc, title;
    private ImageView back;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        inits();
    }

    private void inits() {
        RealmConfiguration config =
                new RealmConfiguration.Builder()
                        .allowWritesOnUiThread(true)
                        .deleteRealmIfMigrationNeeded()
                        .build();
        Realm.setDefaultConfiguration(config);

        realm=Realm.getDefaultInstance();
        desc = findViewById(R.id.et_description);
        title = findViewById(R.id.et_title);
        back = findViewById(R.id.iv_back);
        back.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        saveData();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveData() {
        if (!desc.getText().toString().isEmpty() || !title.getText().toString().isEmpty()) {
            //SET DATA
            String id = UUID.randomUUID().toString();
            String titleS = title.getText().toString();
            String descS = desc.getText().toString();
            if (titleS.isEmpty()){
                titleS="Text note";
            }
            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd");
            LocalDateTime now = LocalDateTime.now();
            String time = timeFormat.format(now);
            String data = dateFormat.format(now);
            Note note = new Note(id, titleS, descS, data, time);
            //SAVE
            RealmHelper helper = new RealmHelper(realm);
            helper.save(note);
        }
    }

}