package com.example.atividade.persistence;

import com.example.atividade.model.Agenda;

import java.util.ArrayList;
import java.util.List;

public class AgendaImpl implements AgendaDao {
    private List lista;
    public AgendaImpl(){lista = new ArrayList();}
    @Override
    public void salvar(Agenda a) {lista.add(a);}

    @Override
    public void editar(Agenda a) {
        if(lista.contains(a)){
            lista.add(lista.indexOf(a),a);
        }
    }

    @Override
    public void remover(Agenda a) {lista.remove(a);}

    @Override
    public List listagem() {
        return lista;
    }
}
