package com.exemplo.api.controller;

import com.exemplo.api.model.Tarefa;
import com.exemplo.api.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {

    @Autowired
    private TarefaRepository repository;

    // Listar todas as tarefas
    @GetMapping
    public List<Tarefa> listar() {
        return repository.findAll();
    }

    // Buscar uma tarefa por ID
    @GetMapping("/{id}")
    public Optional<Tarefa> buscarPorId(@PathVariable Long id) {
        return repository.findById(id);
    }

    // Criar nova tarefa
    @PostMapping
    public Tarefa criar(@RequestBody Tarefa tarefa) {
        return repository.save(tarefa);
    }

    // Atualizar uma tarefa
    @PutMapping("/{id}")
    public Tarefa atualizar(@PathVariable Long id, @RequestBody Tarefa tarefaAtualizada) {
        return repository.findById(id)
                .map(tarefa -> {
                    tarefa.setTitulo(tarefaAtualizada.getTitulo());
                    tarefa.setDescricao(tarefaAtualizada.getDescricao());
                    tarefa.setConcluida(tarefaAtualizada.isConcluida());
                    return repository.save(tarefa);
                })
                .orElseGet(() -> {
                    tarefaAtualizada.setId(id);
                    return repository.save(tarefaAtualizada);
                });
    }

    // Deletar uma tarefa
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
