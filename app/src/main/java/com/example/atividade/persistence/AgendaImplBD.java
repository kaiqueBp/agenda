package com.example.atividade.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.atividade.model.Agenda;

import java.util.ArrayList;
import java.util.List;

public class AgendaImplBD implements AgendaDao {
    DBhelper db;
    @Override
    public void salvar(Agenda a) {
        ContentValues dados = new ContentValues();
        dados.put("titulo",a.getTitulo());
        dados.put("descricao",a.getDescricao());
        dados.put("status",a.getStatus());
        db.getWritableDatabase().insertOrThrow("agenda",null,dados);
        db.close();
    }

    @Override
    public void editar(Agenda a) {
        ContentValues dados = new ContentValues();
        dados.put("titulo",a.getTitulo());
        dados.put("descricao",a.getDescricao());
        dados.put("status",a.getStatus());
        db.getWritableDatabase().update("agenda",dados,"id=?",new String[]{a.getId()+""});
        db.close();
    }

    @Override
    public void remover(Agenda a) {
        db.getWritableDatabase().delete("agenda","id=?",new String[]{a.getId()+""});
        db.close();
    }

    public AgendaImplBD(Context context){
        this.db=new DBhelper(context);
    }

    @Override
    public List listagem() {
        List retorno = new ArrayList();
        String colunas[] = {"id","titulo","descricao","status"};
        Cursor cursor = db.getReadableDatabase().query("agenda", colunas, null,
                null, null, null, "titulo");

        final int COLUMN_ID=0, CULUMN_TITULO=1, COLUMN_DESCRICAO=2, COLUMN_ST=3;
        while(cursor.moveToNext()){
            Agenda a = new Agenda();
            a.setId(cursor.getInt(COLUMN_ID));
            a.setTitulo(cursor.getString(CULUMN_TITULO));
            a.setDescricao(cursor.getString(COLUMN_DESCRICAO));
            a.setStatus(cursor.getString(COLUMN_ST));
            retorno.add(a);
        }
        db.close();

        return retorno;
    }
}
