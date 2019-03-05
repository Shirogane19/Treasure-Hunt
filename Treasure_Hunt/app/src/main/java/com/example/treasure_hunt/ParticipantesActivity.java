package com.example.treasure_hunt;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ParticipantesActivity extends AppCompatActivity {

    String items[] = new String[]{"Primero","Segundo","Tercero","Cuarto" };
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participantes);
        this.setTitle("Participantes");

        listView = findViewById(R.id.listVwParticipantes);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Toast.makeText(ParticipantesActivity.this,items[position],Toast.LENGTH_SHORT).show();
            }
        });
    }

}
