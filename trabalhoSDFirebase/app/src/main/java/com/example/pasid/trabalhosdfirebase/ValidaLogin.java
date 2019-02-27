package com.example.pasid.trabalhosdfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ValidaLogin extends AppCompatActivity {
    private TextView email, senha;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializaComponentes();
    }

    private void inicializaComponentes() {
        email = (EditText) findViewById(R.id.trabalho);
        senha = (EditText) findViewById(R.id.autor);
    }
    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
        user = Conexao.getFirebaseUser();
        verificaUser();
    }

    private void verificaUser() {
        if(user == null){
            alert("Email não cadastrado!");
            finish();
        }else{
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void alert(String msg){
        Toast.makeText(ValidaLogin.this, msg, Toast.LENGTH_SHORT).show();
    }

}
