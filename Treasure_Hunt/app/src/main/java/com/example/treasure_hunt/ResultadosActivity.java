package com.example.treasure_hunt;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ResultadosActivity extends AppCompatActivity {

    String items[] = new String[]{"Primero","Segundo","Tercero","Cuarto","Quinto","Primero","Segundo","Tercero","Cuarto","Quinto",
            "Primero","Segundo","Tercero","Cuarto","Quinto","Primero","Segundo","Tercero","Cuarto","Quinto","Primero","Segundo",
            "Tercero","Cuarto","Quinto"};
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        this.setTitle("Resultados");

        listView = findViewById(R.id.listVwResultados);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position,convertView,parent);
                TextView txtVw = (TextView) view.findViewById(android.R.id.text1);
                txtVw.setTextColor(Color.WHITE);
                txtVw.setTextSize(20);
                return view;
            }
        };

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openDialog();
            }
        });
    }

    public void openDialog(){
        Dialog myDialog = new Dialog(this);
        myDialog.setContentView(R.layout.dialog_foto);
        myDialog.show();
    }
}
