package com.example.atividade;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.CompoundButtonCompat;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;

import com.example.atividade.model.Agenda;
import com.example.atividade.persistence.AgendaDao;
import com.example.atividade.persistence.AgendaImplBD;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText titulo;
    private EditText descricao;
    private  EditText status;
    private ListView listagem;
    private Button botao;
    private List<Agenda> dados;
    private AgendaDao dao;
    private Agenda a;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapeamentoXML();
        consulta();
        vinculaAdapterALista();
        acoes();

    }
    private void mapeamentoXML(){
        setContentView(R.layout.activity_main);
        titulo=findViewById(R.id.idTitulo);

        descricao=findViewById(R.id.idDescricao);

        status=findViewById(R.id.idSt);

        botao=findViewById(R.id.idOk);
        listagem=findViewById(R.id.idL);//listagem

    }
    private void consulta(){
        if(dao==null)
            dao= new AgendaImplBD(this);
        dados=dao.listagem();

    }

    private void vinculaAdapterALista() {

        listagem.setAdapter(
                new ArrayAdapter(getApplicationContext(),
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        dados)// cria o adapter
        );//
    }

    private void acoes() {
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(titulo.getText().toString().isEmpty()){
                    titulo.setError("Campo está vazio!!");} else if (descricao.getText().toString().isEmpty()) {
                    descricao.setError("Campo está vazio!!");
                } else if (status.getText().toString().isEmpty()) {
                    status.setError("Campo está vazio!!");
                }else{
                if(a==null)
                    a = new Agenda();
                a.setTitulo(titulo.getText().toString());
                a.setDescricao(descricao.getText().toString());
                a.setStatus(status.getText().toString());
                if(a.getId()==null)
                    dao.salvar(a);
                else dao.editar(a);
                limpaCampos();
                atualizaDados();
            }}
        });

        listagem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int indice, long l) {
                new  AlertDialog.Builder(listagem.getContext())
                        .setTitle("Realmente deseja excluir")
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).
                        setNeutralButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dao.remover(dados.get(indice));
                                atualizaDados();
                            }
                        })
                        .create().show();

                return false;
            }
        });
        listagem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                a=dados.get(i);
                titulo.setText(a.getTitulo());
                descricao.setText(a.getDescricao());
                status.setText(a.getStatus());
            }
        });
    }

    private void atualizaDados() {
        dados.clear();
        dados.addAll(dao.listagem());
        ((ArrayAdapter) listagem.getAdapter()
        ).notifyDataSetChanged();
    }

    private void limpaCampos() {
        titulo.setText("");
        descricao.setText("");
        status.setText("");
        a=null;
    }

    public void cancelar(View view){
        AlertDialog.Builder acao = new AlertDialog.Builder(this);
        acao.setTitle("Você quer sair");
        acao.setItems(new CharSequence[]{"Sair"},new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        acao.create().show();
    }
}