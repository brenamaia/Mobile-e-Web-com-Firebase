package com.example.pasid.trabalhosdfirebase;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pasid.trabalhosdfirebase.modelo.Trabalho;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cadastro extends AppCompatActivity {
    //private Button btnCadastrar, btnVoltar;
    private FirebaseAuth auth;

    EditText edtNome, edtAutor, edtLing, edtEndereco, edtUser, edtSenha;
    ListView listV_dados;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    Trabalho trabalhoSelecionado;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro);
        inicializaComponentes();

        inicializarFirebase();
    }


    private void inicializarFirebase(){
        //FirebaseApp.initializeApp(Cadastro.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }

    private void inicializaComponentes() {
        edtNome = (EditText) findViewById(R.id.trabalho);
        edtAutor = (EditText) findViewById(R.id.autor);
        edtLing = (EditText) findViewById(R.id.linguagem);
        edtEndereco = (EditText) findViewById(R.id.end_git);
        edtUser = (EditText) findViewById(R.id.userGit);
        edtSenha = (EditText) findViewById(R.id.senhaGit);
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menup, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_salvar){
            Trabalho trab = new Trabalho();
            trab.setId(UUID.randomUUID().toString());
            trab.setNome(edtNome.getText().toString());
            trab.setAutor(edtAutor.getText().toString());
            trab.setLinguagem(edtLing.getText().toString());
            trab.setEnderecoGit(edtEndereco.getText().toString());
            trab.setUserGit(edtUser.getText().toString());
            trab.setSenhaGit(edtSenha.getText().toString());

            databaseReference.child("Trabalho").child(trab.getUserGit()).setValue(trab);
            alert("Trabalho cadastrado!");
            /*Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);*/
            finish();
            //limparCampos();
        }else if(id == R.id.menu_voltar){

            finish();
        }
        return true;
    }
/*
    private void limparCampos() {
        edtNome.setText("");
        edtSenha.setText("");
        edtUser.setText("");
        edtEndereco.setText("");
        edtLing.setText("");
        edtAutor.setText("");
    }*/

    private void alert(String msg){
        Toast.makeText(Cadastro.this, msg, Toast.LENGTH_SHORT).show();
    }
}

