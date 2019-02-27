package com.example.pasid.trabalhosdfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private Button btnLogar;
    private TextView txtResetSenha;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        inicializaComponentes();
        eventoClicks();
    }


    private void eventoClicks() {
        btnLogar = findViewById(R.id.btn_login);
        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editEmail = findViewById(R.id.emailLogin);
                editSenha = findViewById(R.id.senhaLogin);

                auth = FirebaseAuth.getInstance();
                if (!editSenha.getText().toString().equals("") || !editEmail.getText().toString().equals("")) {
                    (auth.signInWithEmailAndPassword(editEmail.getText().toString(), editSenha.getText().toString())).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(i);
                                finish();
                            } else {
                                alert("Email ou senha incorreto!");
                            }
                        }
                    });
                }
            }
        });
    }



    private void inicializaComponentes(){
        editEmail = (EditText) findViewById(R.id.emailLogin);
        editSenha = (EditText) findViewById(R.id.senhaLogin);
        btnLogar = (Button) findViewById(R.id.btn_login);
    }

    private void alert(String msg){
        Toast.makeText(Login.this, msg, Toast.LENGTH_SHORT).show();
    }
}
