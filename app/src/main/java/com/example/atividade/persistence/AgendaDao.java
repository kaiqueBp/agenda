package com.example.atividade.persistence;

import com.example.atividade.model.Agenda;

import java.util.List;

public interface AgendaDao {
    public void salvar(Agenda a);

    public void editar(Agenda a);

    public void remover(Agenda a);

    public List listagem();
}
