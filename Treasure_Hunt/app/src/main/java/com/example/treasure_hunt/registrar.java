package com.example.treasure_hunt;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registrar extends AppCompatActivity {

    //EditText password ;
    private EditText txtCorreo;
    private EditText txtPassw;
    private EditText txtConfirPassw;
    private Button btnRegistrar;
    private ProgressDialog progressDialog;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private FirebaseAuth firebaseAuth;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    //-----------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        this.setTitle(R.string.registro);

        iniciarFirebaseDatatBase();
        iniciarFireBaseAuth();

        progressDialog = new ProgressDialog(this);

        txtCorreo = findViewById(R.id.edTxtCorreoLog);
        txtPassw = findViewById(R.id.edTxtPassword);
        txtConfirPassw = findViewById(R.id.edTxtConfirmPassword);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar();
            }
        });

    }
    //-----------------------------------------------------------------------------
    private void registrar(){
        if (isNetDisponible() && validarVacio() && validarContrasenna() && validarCorreo()){

            progressDialog.setMessage(getString(R.string.registrando));
            progressDialog.show();

            //Persona p = new Persona();
            //p.setId(UUID.randomUUID().toString());
            //p.setCorreo(txtCorreo.getText().toString());
            //p.setPassword(txtPassw.getText().toString());
            //databaseReference.child("Persona").child(p.getId()).setValue(p);

            String email = txtCorreo.getText().toString().trim();
            String password = txtPassw.getText().toString().trim();

            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast .makeText(registrar.this, R.string.registrado, Toast.LENGTH_LONG).show();
                            }else{
                                if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                    Toast .makeText(registrar.this, R.string.existeUsuario, Toast.LENGTH_LONG).show();
                                }else {
                                    Toast .makeText(registrar.this, R.string.noRegitrado, Toast.LENGTH_LONG).show();
                                }
                                //Log.w("Error", "createUserWithEmail:failure", task.getException());
                            }
                            progressDialog.dismiss();
                        }
                    });
        }
    }
    //-----------------------------------------------------------------------------
    private boolean validarContrasenna(){
        if((txtPassw.getText().toString()).equals(txtConfirPassw.getText().toString())){
            return true;
        }else{
            Toast toast = Toast.makeText(this, R.string.diferenteContrasenna, Toast.LENGTH_LONG);
            toast.show();
            return false;
        }
    }
    //-----------------------------------------------------------------------------
    private boolean validarVacio(){
        if(TextUtils.isEmpty(txtCorreo.getText().toString()) || TextUtils.isEmpty(txtPassw.getText().toString())
                || TextUtils.isEmpty(txtConfirPassw.getText().toString())){
            Toast toast = Toast.makeText(this, R.string.campos_vacios, Toast.LENGTH_LONG);
            toast.show();
            return false;
        }else {
            return true;
        }
    }
    //-----------------------------------------------------------------------------
    private boolean validarCorreo(){
        if (txtCorreo.getText().toString().trim().matches(emailPattern)) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(),R.string.correo_invalido,Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    //-----------------------------------------------------------------------------
    private boolean isNetDisponible() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // Si hay conexión a Internet en este momento
            return true;
        } else {
            // No hay conexión a Internet en este momento
            Toast .makeText(registrar.this, R.string.noInternet, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    //-----------------------------------------------------------------------------
    private void iniciarFirebaseDatatBase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }
    //-----------------------------------------------------------------------------
    private void iniciarFireBaseAuth() {
        firebaseAuth = firebaseAuth.getInstance();
    }
    //-----------------------------------------------------------------------------
}
