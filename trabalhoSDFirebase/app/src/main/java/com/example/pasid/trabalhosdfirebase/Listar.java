package com.example.pasid.trabalhosdfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.pasid.trabalhosdfirebase.modelo.Trabalho;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Listar extends AppCompatActivity {

    ListView listV_dados;
    private EditText dadosTrab, dadosAutor, dadosLing, dadosGit, dadosUser, dadosSenha;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    Trabalho trabalhoSelecionado;

    private List<Trabalho> listTrabalho = new ArrayList<Trabalho>();
    private ArrayAdapter<Trabalho> arrayAdapterTrabalho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listar);

        inicializaComponentes();
        inicializarFirebase();
        eventoDatabase();

        listV_dados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                trabalhoSelecionado = (Trabalho) parent.getItemAtPosition(position);
                dadosTrab.setText(trabalhoSelecionado.getNome());
                dadosAutor.setText(trabalhoSelecionado.getAutor());
                dadosLing.setText(trabalhoSelecionado.getLinguagem());
                dadosGit.setText(trabalhoSelecionado.getEnderecoGit());
                dadosUser.setText(trabalhoSelecionado.getUserGit());
                dadosSenha.setText(trabalhoSelecionado.getSenhaGit());
            }
        });

    }

    private void inicializaComponentes() {
        dadosTrab = (EditText) findViewById(R.id.ddTrab);
        dadosAutor = (EditText) findViewById(R.id.ddAutor);
        dadosLing = (EditText) findViewById(R.id.ddLing);
        dadosGit = (EditText) findViewById(R.id.ddEnd);
        dadosUser = (EditText) findViewById(R.id.ddUser);
        dadosSenha = (EditText) findViewById(R.id.ddSenha);
        listV_dados = (ListView) findViewById(R.id.listView);

    }

    private void eventoDatabase() {
        databaseReference.child("Trabalho").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listTrabalho .clear();
                for(DataSnapshot objSnapshot:dataSnapshot.getChildren()){
                    Trabalho t = objSnapshot.getValue(Trabalho.class);
                    listTrabalho.add(t);
                }
                arrayAdapterTrabalho = new ArrayAdapter<>(Listar.this,
                        android.R.layout.simple_list_item_1, listTrabalho);
                listV_dados.setAdapter(arrayAdapterTrabalho);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void inicializarFirebase() {
        //FirebaseApp.initializeApp(Listar.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        //firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.menu_voltarEd){
            /*Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);*/
            finish();
        }else if(id == R.id.menuEditarEd){

            Trabalho trab = new Trabalho();
            trab.setId(trabalhoSelecionado.getUserGit());
            trab.setNome(dadosTrab.getText().toString().trim());
            trab.setAutor(dadosAutor.getText().toString().trim());
            trab.setLinguagem(dadosLing.getText().toString().trim());
            trab.setEnderecoGit(dadosGit.getText().toString().trim());
            trab.setUserGit(dadosUser.getText().toString().trim());
            trab.setSenhaGit(dadosSenha.getText().toString().trim());

            databaseReference.child("Trabalho").child(trab.getUserGit()).setValue(trab);
            alert("Trabalho Alterado!");
            limparCampos();
        }else if(id == R.id.menu_excluir){
            Trabalho trab = new Trabalho();
            trab.setId(trabalhoSelecionado.getUserGit());
            databaseReference.child("Trabalho").child(trab.getId()).removeValue();
            alert("Trabalho Excluido!");
            limparCampos();
        }
        return true;
    }



    private void limparCampos() {
        dadosTrab.setText("");
        dadosAutor.setText("");
        dadosLing.setText("");
        dadosGit.setText("");
        dadosUser.setText("");
        dadosSenha.setText("");
    }

    private void alert(String msg){
        Toast.makeText(Listar.this, msg, Toast.LENGTH_SHORT).show();
    }

}
