package com.example.treasure_hunt;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class login extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    private EditText edTxtCorreo;
    private EditText edTxtPassw;
    private Button btnRegistrar;
    private Button btnLogin;
    private ProgressDialog progressDialog;
    //----------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.setTitle(getString(R.string.app_name));

        firebaseAuth = firebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        edTxtCorreo = findViewById(R.id.edTxtCorreoLog);
        edTxtPassw = findViewById(R.id.edTxtPasswLog);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });
        btnRegistrar = findViewById(R.id.btnRegistrarLog);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarNuevoUsuario();
            }
        });
    }
    //----------------------------------------------------------------------
    private void registrarNuevoUsuario() {
        Intent intent = new Intent(getApplication(),registrar.class);
        startActivity(intent);
    }
    //----------------------------------------------------------------------
    private void iniciarSesion() {

        if ( isNetDisponible() && validarVacio() ) {
            progressDialog.setMessage(getString(R.string.cargaComprobar));
            progressDialog.show();

            final String emailLog = edTxtCorreo.getText().toString().trim();
            String passwordLog = edTxtPassw.getText().toString().trim();

            firebaseAuth.signInWithEmailAndPassword(emailLog, passwordLog)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(login.this, getString(R.string.exito), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplication(), LandingActivity.class);
                                intent.putExtra(LandingActivity.user, emailLog);
                                startActivity(intent);
                                finish();
                            } else {
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    Toast.makeText(login.this, getString(R.string.contrasennaInvalida), Toast.LENGTH_LONG).show();
                                }
                                if (task.getException() instanceof FirebaseAuthInvalidUserException) {
                                    Toast.makeText(login.this, getString(R.string.contrasennaInvalida), Toast.LENGTH_LONG).show();
                                }
                                Log.w("Error", "Fallo login", task.getException());
                                //Toast.makeText(login.this, "Error." + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
        }
    }

    //-----------------------------------------------------------------------------
    private boolean validarVacio(){
        if( TextUtils.isEmpty(edTxtCorreo.getText().toString()) || TextUtils.isEmpty(edTxtPassw.getText().toString()) ){
            Toast toast = Toast.makeText(this, R.string.campos_vacios, Toast.LENGTH_LONG);
            toast.show();
            return false;
        }else {
            return true;
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
            Toast .makeText(login.this, R.string.noInternet, Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    //-----------------------------------------------------------------------------
}
